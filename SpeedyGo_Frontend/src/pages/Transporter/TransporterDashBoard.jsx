// TransporterDashBoard.js
import React, { useState, useEffect } from 'react';
import '../../styles/TransporterDashBoard.css';
import axios from 'axios';
import { useSelector, useDispatch } from 'react-redux';
import CurrentRideTableTransporter from "./CurrentRideTableTransporter";
import HistoryTableTransporter from './HistoryTableTransporter';
import Cookies from "js-cookie";
import Swal from 'sweetalert2';
import { FaCar, FaChartLine, FaHistory, FaSignOutAlt } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';
import { setUser } from "../../Redux/Reducer";
import { TRANSPORTERDASHBOARD_TABS, TRANSPORTERDASHBOARD_BUTTON_TITLES, TRANSPORTERDASHBOARD_AVAILABILITY, TRANSPORTERDASHBOARD_MESSAGES, TRANSPORTERDASHBOARD_ENDPOINTS } from '../../constants';
import DashedNumber from './DashedNumber';
import CustomButton from '../../Components/CustomComponents/CustomButton';
import CustomFormInput from '../../Components/CustomComponents/CustomFormInput';
import Statistics from './Statistics';

function TransporterDashBoard() {
  const [transporterData, setTransporterData] = useState([]);
  const [activeTab, setActiveTab] = useState(TRANSPORTERDASHBOARD_TABS.CURRENT_RIDE);
  const email = useSelector(state => state.user.email);
  const token = Cookies.get('token');
  const [vehicleInfo, setVehicleInfo] = useState(null);
  const [isAvailable, setIsAvailable] = useState(true);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [basePrice, setBasePrice] = useState();
  const [pricePerKm, setPricePerKm] = useState();
  const [isEditing, setIsEditing] = useState(false);

  const toggleAvailability = () => {
    setIsAvailable(!isAvailable);
    isAvailable ? updateVehicleStatus("NOT_AVAILABLE") : updateVehicleStatus("AVAILABLE");
  };

  const fetchBasePricePricePerKm = async (vehicleNumber) => {
    console.log("Vehicle Number:", vehicleNumber);
    try {
      const response = await axios.get(`${TRANSPORTERDASHBOARD_ENDPOINTS.BASE_PRICE_PRICE_PER_KM}/${vehicleNumber}`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      console.log(response);
      setBasePrice(response.data.basePrice);
      setPricePerKm(response.data.pricePerKilometer);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };
  const fetchStatusAvailability = async () => {
    try {
      const response = await axios.get(`${TRANSPORTERDASHBOARD_ENDPOINTS.VEHICLE_INFO}${email}`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      setVehicleInfo(response.data);
      fetchBasePricePricePerKm(response.data.vehicleNumber);
      setIsAvailable(response.data.status === 'AVAILABLE');
    } catch (error) {
      Swal.fire(TRANSPORTERDASHBOARD_MESSAGES.ERROR, TRANSPORTERDASHBOARD_MESSAGES.SOMETHING_WENT_WRONG, 'error');
    }
  };

  const fetchTransporterData = async () => {
    try {
      const response = await axios.get(`${TRANSPORTERDASHBOARD_ENDPOINTS.ACCEPTED_OR_PENDING}${email}`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      setTransporterData(response.data);
    } catch (error) {
      Swal.fire(TRANSPORTERDASHBOARD_MESSAGES.ERROR, TRANSPORTERDASHBOARD_MESSAGES.SOMETHING_WENT_WRONG, 'error');
    }
  };

  useEffect(() => {
    fetchTransporterData();
  }, []);

  useEffect(() => {
    fetchStatusAvailability();
  }, []);

  const updateVehicleStatus = async (vehicleStatus) => {
    try {
      const response = await axios.put(`${TRANSPORTERDASHBOARD_ENDPOINTS.UPDATE_VEHICLE_STATUS}${vehicleInfo.vehicleNumber}/status`, null, {
        params: {
          status: vehicleStatus
        },
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
      Swal.fire(TRANSPORTERDASHBOARD_MESSAGES.SUCCESS, response.data, 'success');
    } catch (error) {
      Swal.fire(TRANSPORTERDASHBOARD_MESSAGES.ERROR, TRANSPORTERDASHBOARD_MESSAGES.SOMETHING_WENT_WRONG, 'error');
    }
  };

  const handleLogout = () => {
    dispatch(setUser({ email: "", name: "", role: "", phoneNumber: "" }));
    Cookies.remove('token');
    navigate("/");
  };

  const handleEditSubmit = async () => {
    console.log(basePrice);
    console.log(pricePerKm);
  
    try {
      const updatePricesUrl = `${TRANSPORTERDASHBOARD_ENDPOINTS.UPDATE_PRICES}/${vehicleInfo.vehicleNumber}/updatePricing`;
  
      await axios.put(updatePricesUrl, null, {
        params: {
          basePrice: basePrice,
          pricePerKilometer: pricePerKm
        },
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
  
      Swal.fire(TRANSPORTERDASHBOARD_MESSAGES.SUCCESS, 'Prices updated successfully!', 'success');
      setIsEditing(false);
      fetchBasePricePricePerKm(vehicleInfo.vehicleNumber);
    } catch (error) {
      Swal.fire(TRANSPORTERDASHBOARD_MESSAGES.ERROR, TRANSPORTERDASHBOARD_MESSAGES.SOMETHING_WENT_WRONG, 'error');
    }
  };
  

  return (
    <div className="dashboard-container">
      <div className="sidebar">
        <h2>Dashboard</h2>
        <div className="button-group">
          <button className="sidebar-button" onClick={() => setActiveTab(TRANSPORTERDASHBOARD_TABS.CURRENT_RIDE)}>
            <FaCar className="icon" /> {TRANSPORTERDASHBOARD_BUTTON_TITLES.CURRENT_RIDE}
          </button>
          <button className="sidebar-button" onClick={() => setActiveTab(TRANSPORTERDASHBOARD_TABS.HISTORY)}>
            <FaHistory className="icon" /> {TRANSPORTERDASHBOARD_BUTTON_TITLES.HISTORY}
          </button>
          <button className="sidebar-button" onClick={()=> setActiveTab(TRANSPORTERDASHBOARD_TABS.STATISTICS)}>
            <FaChartLine className="icon" /> {TRANSPORTERDASHBOARD_BUTTON_TITLES.STATISTICS}
          </button>
          <button className="sidebar-button" onClick={handleLogout}>
            <FaSignOutAlt className="icon" /> {TRANSPORTERDASHBOARD_BUTTON_TITLES.LOGOUT}
          </button>
        </div>
        <div className="availability-toggle">
          <span className='availability-text'>{isAvailable ? TRANSPORTERDASHBOARD_AVAILABILITY.AVAILABLE : TRANSPORTERDASHBOARD_AVAILABILITY.NOT_AVAILABLE}</span>
          <label className="switch">
            <input type="checkbox" checked={isAvailable} onChange={toggleAvailability} />
            <span className="slider"></span>
          </label>
        </div>
        <div className="pricing-info">
          <p>
            Base Price:
            {isEditing ? (
              <CustomFormInput
                type="number"
                value={basePrice}
                onChange={(e) => setBasePrice(Number(e.target.value))}
              />
            ) : (
              <span><DashedNumber value={basePrice} /></span>
            )}
          </p>
          <p>
            Price Per Km:
            {isEditing ? (
              <CustomFormInput
                type="number"
                value={pricePerKm}
                onChange={(e) => setPricePerKm(Number(e.target.value))}
              />
            ) : (
              <span><DashedNumber value={pricePerKm} /></span>
            )}
          </p>
          <CustomButton
            buttonTitle={isEditing ? 'Submit' : 'Edit'}
            onClick={() => isEditing ? handleEditSubmit() : setIsEditing(true)}
          />
        </div>
      </div>
      <div className="content">
      {activeTab === TRANSPORTERDASHBOARD_TABS.CURRENT_RIDE ? (
        <CurrentRideTableTransporter data={transporterData} fetchTransporterData={fetchTransporterData} />
      ) : activeTab === TRANSPORTERDASHBOARD_TABS.HISTORY ? (
        <HistoryTableTransporter />
      ) : activeTab === TRANSPORTERDASHBOARD_TABS.STATISTICS ? (
        <Statistics />
      ) : null}
    </div>
    </div>
  );
}

export default TransporterDashBoard;
