import React, { useState, useEffect, useRef } from 'react';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import 'leaflet-routing-machine';
import axios from 'axios';
import { useFormik } from 'formik';
import "../../styles/UserStyles/Mapcomponent.css";
import CustomFormInput from '../CustomComponents/CustomFormInput';
import CustomButton from '../CustomComponents/CustomButton';
import iconUrl from 'leaflet/dist/images/marker-icon.png'; // Adjust the path if necessary
import iconShadowUrl from 'leaflet/dist/images/marker-shadow.png';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2'; // Import sweetalert2
import Cookies from 'js-cookie';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { mapValidationSchema, ENDPOINTS, SWAL_MESSAGES, ERROR_MESSAGES, UI_TEXT ,transporterProfile} from '../../constants';

const customIcon = new L.Icon({
  iconUrl: iconUrl,
  shadowUrl: iconShadowUrl,
  iconSize: [25, 41], // Size of the icon
  iconAnchor: [12, 41], // Point of the icon which will correspond to marker's location
  shadowSize: [41, 41] // Size of the shadow
});

function MapComponent() {
  const [routingControl, setRoutingControl] = useState(null);
  const [distance, setDistance] = useState(null);
  const [transporterData, setTransporterData] = useState([]);
  const [isLoading, setIsLoading] = useState(false); // New state to control button
  const navigate = useNavigate();
  const mapRef = useRef(null); // Ref for the map instance
  const mapContainerRef = useRef(null); // Ref for the map container
  const [datetimepicker, setDateTimepicker] = useState(false);
  const [date, setDate] = useState(null); // Default to current date
  const [time, setTime] = useState(null); // Default to current time

  useEffect(() => {
    if (!mapRef.current) {
      const mapInstance = L.map(mapContainerRef.current).setView([12.9716, 77.5946], 13); // Default location Bangalore
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
      }).addTo(mapInstance);

      mapRef.current = mapInstance;
    }
  }, []);

  const getCoordinates = async (address) => {
    try {
      const formattedAddress = `${address}, India`;
      const response = await axios.get(`${ENDPOINTS.NOMINATIM_SEARCH}${formattedAddress}`);

      if (response.status !== 200) {
        throw new Error(`${ERROR_MESSAGES.FETCH_COORDINATES_ERROR} ${response.status}`);
      }

      const data = response.data;
      if (data.length > 0) {
        return {
          lat: parseFloat(data[0].lat),
          lon: parseFloat(data[0].lon),
        };
      } else {
        throw new Error(ERROR_MESSAGES.LOCATION_NOT_FOUND);
      }
    } catch (error) {
      return null; // Return null if an error occurs
    }
  };

  const calculateRoute = async (values) => {
    setIsLoading(true); // Disable the button when calculating
    const { source, destination } = values;

    try {
      // Fetch coordinates for source and destination in parallel
      const [sourceCoords, destinationCoords] = await Promise.all([
        getCoordinates(source),
        getCoordinates(destination),
      ]);

      // Check if both are invalid and show a single alert
      if (!sourceCoords && !destinationCoords) {
        Swal.fire('Error', SWAL_MESSAGES.BOTH_INVALID, 'error');
        setDistance(null);
        setIsLoading(false);
        return;
      }

      // Check if only source is invalid
      if (!sourceCoords) {
        Swal.fire('Error', SWAL_MESSAGES.SOURCE_INVALID, 'error');
        setDistance(null);
        setIsLoading(false);
        return;
      }

      // Check if only destination is invalid
      if (!destinationCoords) {
        Swal.fire('Error', SWAL_MESSAGES.DESTINATION_INVALID, 'error');
        setDistance(null);
        setIsLoading(false);
        return;
      }

      // Remove existing route if any
      if (routingControl) {
        mapRef.current.removeControl(routingControl);
      }

      // Add new routing control and calculate route
      const routing = L.Routing.control({
        waypoints: [
          L.latLng(sourceCoords.lat, sourceCoords.lon),
          L.latLng(destinationCoords.lat, destinationCoords.lon),
        ],
        routeWhileDragging: true,
      }).addTo(mapRef.current);

      setRoutingControl(routing);

      // On successful route calculation, set the distance
      routing.on('routesfound', function (e) {
        const distanceInKm = e.routes[0].summary.totalDistance / 1000; // distance in km
        setDistance(distanceInKm.toFixed(2)); // limit to 2 decimal places
        setIsLoading(false); // Re-enable the button after successful calculation
      });      // Add markers for source and destination
      L.marker([sourceCoords.lat, sourceCoords.lon], { icon: customIcon }).addTo(mapRef.current);
      L.marker([destinationCoords.lat, destinationCoords.lon], { icon: customIcon }).addTo(mapRef.current);

    } catch (error) {
      console.error(ERROR_MESSAGES.ROUTE_ERROR, error);
      Swal.fire('Error', SWAL_MESSAGES.ROUTE_ERROR, 'error');
      setIsLoading(false); // Re-enable the button in case of error
    }
  };

  const formik = useFormik({
    initialValues: {
      source: '',
      destination: '',
      date: null,
      time: null
    },
    validationSchema: mapValidationSchema,
    onSubmit: async (values) => {
      await calculateRoute(values);
    },
  });

  useEffect(() => {
    const fetchAvailableBookings = async () => {
      if (distance !== null) {
        const token = Cookies.get('token');
        console.log("distance: " + distance);
        console.log("token: " + token);

        try {
          const response = await axios.get(ENDPOINTS.AVAILABLE_BOOKINGS, {
            params: {
              distance: distance // Ensure 'distance' is defined and correct
            },
            headers: {
              'Authorization': `Bearer ${token}`
            }
          });
          setTransporterData(response.data);
        } catch (error) {
          console.error(ERROR_MESSAGES.FETCH_BOOKINGS_ERROR, error);
          if (error.response) {
             Swal.fire('Error', SWAL_MESSAGES.FETCH_BOOKINGS_ERROR, 'error');
          }
        }
      }
    };

    if (distance !== null) {
      fetchAvailableBookings();
    }
  }, [distance]);

  const handleDateChange = (date) => {
    setDate(date);
    formik.setFieldValue('date', date);
  };

  const handleTimeChange = (time) => {
    setTime(time);
    formik.setFieldValue('time', time);
  };

  const formatDate = (date) => {
    // Convert date to ISO string and slice to get only the date part
    return date.toISOString().split('T')[0]; // YYYY-MM-DD format
  };

  const formatTime = (time) => {
    // Ensure the time is in the format HH:mm:ss
    const hours = time.getHours().toString().padStart(2, '0');
    const minutes = time.getMinutes().toString().padStart(2, '0');
    const seconds = time.getSeconds().toString().padStart(2, '0');

    return `${hours}:${minutes}:${seconds}`;
  };

  const handleQuotation = () => {
    const riderData = {
      source: formik.values.source,
      destination: formik.values.destination,
      rideDate: formatDate(date),
      distance: distance,
      rideTime: formatTime(time)
    };
    navigate(transporterProfile, { state: { transporter: transporterData, riderData: riderData } });
  };

  const handleDatePicker = () => {
    setDateTimepicker(!datetimepicker);
  };

  const isToday = date ? date.toDateString() === new Date().toDateString() : false;
  const currentTime = new Date();

  return (
    <div className="app-container">
      <h1 className="app-title">{UI_TEXT.APP_TITLE}</h1>

      <div className="mapinput-container">
        <form onSubmit={formik.handleSubmit} className="input-fields">
          {!datetimepicker ? (
            <>
              <CustomFormInput
                type="text"
                id="source"
                name="source"
                value={formik.values.source}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
                placeholder={UI_TEXT.SOURCE_PLACEHOLDER}
              />
              {formik.touched.source && formik.errors.source && (
                <div className="error-message">{formik.errors.source}</div>
              )}

              <CustomFormInput
                type="text"
                id="destination"
                name="destination"
                value={formik.values.destination}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
                placeholder={UI_TEXT.DESTINATION_PLACEHOLDER}
              />
              {formik.touched.destination && formik.errors.destination && (
                <div className="error-message">{formik.errors.destination}</div>
              )}

              <CustomButton type="submit" buttonTitle={UI_TEXT.CALCULATE_ROUTE_BUTTON} disabled={isLoading} style={{marginTop:"25px"}} />
              {distance && <p className="distance-display">Distance: {distance} km</p>}
              {distance && (
                <CustomButton onClick={handleDatePicker} buttonTitle={UI_TEXT.SCHEDULE_RIDE_BUTTON} />
              )}
            </>
          ) : (
            <div className="date-time-picker">
              <DatePicker
                selected={date}
                onChange={handleDateChange}
                dateFormat="yyyy-MM-dd"
                placeholderText={UI_TEXT.DATE_PLACEHOLDER}
                className="date-picker"
                minDate={new Date()} // Set minimum date to today
              />
              {formik.touched.date && formik.errors.date && (
                <div className="error-message">{formik.errors.date}</div>
              )}

              <DatePicker
                selected={time}
                onChange={handleTimeChange}
                showTimeSelect
                showTimeSelectOnly
                timeIntervals={15}
                timeCaption="Time"
                dateFormat="HH:mm:ss"
                placeholderText={UI_TEXT.TIME_PLACEHOLDER}
                className="time-picker"
                minTime={
                  isToday
                    ? currentTime // Set minimum time to now if today
                    : new Date().setHours(0, 0, 0, 0) // Midnight if any other day
                }
                maxTime={new Date().setHours(23, 59, 59)} // Set maximum time to the end of the day
              />
              {formik.touched.time && formik.errors.time && (
                <div className="error-message">{formik.errors.time}</div>
              )}
              <div className='searchrider-button'>
                <CustomButton onClick={handleDatePicker} buttonTitle={UI_TEXT.BACK_BUTTON} />
                <CustomButton onClick={handleQuotation} buttonTitle={UI_TEXT.GET_QUOTATION_BUTTON} disabled={!formik.values.date || !formik.values.time} />
              </div>
            </div>
          )}
        </form>
        <div className="map-container" ref={mapContainerRef}></div>
      </div>
    </div>
  );
};

export default MapComponent;

