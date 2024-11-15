import axios from 'axios';
import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import Swal from 'sweetalert2';
import Cookies from 'js-cookie';
import "../../styles/UserDashBoard.css";
import RideCustomerModal from './RideCustomerModal';
import { PENDINGPAYMENTS_HEADERS, PENDINGPAYMENTS_MESSAGES, PENDINGPAYMENTS_ENDPOINTS, PENDINGPAYMENTS_RAZORPAY_OPTIONS } from '../../constants';
import { FaMoneyBillWave, FaInfoCircle } from 'react-icons/fa';
import { Pagination } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../../styles/UserDashBoard.css';

function PendingPayments() {
  const [paymentData, setPaymentData] = useState([]);
  const [selectedRide, setSelectedRide] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5;
  const token = Cookies.get('token');
  const { email } = useSelector(state => state.user);

  const fetchPayementDetails = async () => {
    try {
      const response = await axios.get(`${PENDINGPAYMENTS_ENDPOINTS.FETCH_PAYMENTS}${email}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      setPaymentData(response.data);
    } catch (error) {
      Swal.fire(PENDINGPAYMENTS_MESSAGES.ERROR, PENDINGPAYMENTS_MESSAGES.SOMETHING_WENT_WRONG, 'error');
    }
  };

  useEffect(() => {
    fetchPayementDetails();
  }, [email, token]);

  const handleDetailsClick = (ride) => {
    setSelectedRide(ride);
  };

  const handleCloseModal = () => {
    setSelectedRide(null);
  };

  const handlePayClick = (ride) => {
    const options = {
      key: PENDINGPAYMENTS_RAZORPAY_OPTIONS.KEY,
      amount: Math.round(ride.totalPrice * 100),
      currency: PENDINGPAYMENTS_RAZORPAY_OPTIONS.CURRENCY,
      name: PENDINGPAYMENTS_RAZORPAY_OPTIONS.NAME,
      description: PENDINGPAYMENTS_RAZORPAY_OPTIONS.DESCRIPTION,
      handler: async function (response) {
        const paymentId = response.razorpay_payment_id;
        console.log("Payment successful with ID:", paymentId);

        try {
          await axios.put(
            `${PENDINGPAYMENTS_ENDPOINTS.UPDATE_RIDE_STATUS}${ride.requestId}`,
            { status: "COMPLETED" },
            { headers: { Authorization: `Bearer ${token}` } }
          );
          fetchPayementDetails();
          Swal.fire(PENDINGPAYMENTS_MESSAGES.SUCCESS, PENDINGPAYMENTS_MESSAGES.PAYMENT_SUCCESS, 'success');
          const data = {
            paymentId: paymentId,
            bookingId: ride.requestId,
            customerEmail: email,
            transporterEmail: ride.transporterInfo.email,
            totalPrice: ride.totalPrice,
            paymentStatus: "SUCCESS"
          };
          await axios.post(PENDINGPAYMENTS_ENDPOINTS.SAVE_PAYMENT, data, {
            headers: { Authorization: `Bearer ${token}` }
          });
        } catch (error) {
          console.error(PENDINGPAYMENTS_MESSAGES.ERROR_UPDATING_STATUS, error);
          Swal.fire(PENDINGPAYMENTS_MESSAGES.ERROR, PENDINGPAYMENTS_MESSAGES.RECORD_PAYMENT_ERROR, 'error');
        }
      },
      theme: {
        color: PENDINGPAYMENTS_RAZORPAY_OPTIONS.THEME_COLOR,
      },
    };

    const pay = new window.Razorpay(options);
    pay.open();
  };

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = paymentData.slice(indexOfFirstItem, indexOfLastItem);

  const renderPagination = () => {
    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(paymentData.length / itemsPerPage); i++) {
      pageNumbers.push(i);
    }

    return (
      <Pagination className="custom-pagination justify-content-center">
        {pageNumbers.map(number => (
          <Pagination.Item key={number} active={number === currentPage} onClick={() => handlePageChange(number)}>
            {number}
          </Pagination.Item>
        ))}
      </Pagination>
    );
  };

  return (
    <div>
      <h2 className="text-center custom-margin-bottom">{PENDINGPAYMENTS_MESSAGES.TITLE}</h2>
      <div className="table table-striped table-responsive">
        {paymentData.length === 0 ? (
          <p className="text-center fw-bold my-2">{PENDINGPAYMENTS_MESSAGES.NO_PENDING_PAYMENTS}</p>
        ) : (
          <>
            <table>
              <thead>
                <tr>
                  <th>{PENDINGPAYMENTS_HEADERS.SOURCE}</th>
                  <th>{PENDINGPAYMENTS_HEADERS.DESTINATION}</th>
                  <th>{PENDINGPAYMENTS_HEADERS.RIDE_DATE}</th>
                  <th>{PENDINGPAYMENTS_HEADERS.RIDE_TIME}</th>
                  <th>{PENDINGPAYMENTS_HEADERS.STATUS}</th>
                  <th>{PENDINGPAYMENTS_HEADERS.DETAILS}</th>
                  <th>{PENDINGPAYMENTS_HEADERS.OTHERS}</th>
                </tr>
              </thead>
              <tbody>
                {currentItems.map((ride) => (
                  <tr key={ride.requestId}>
                    <td>{ride.source}</td>
                    <td>{ride.destination}</td>
                    <td>{ride.rideDate}</td>
                    <td>{ride.rideTime}</td>
                    <td>
                      <span>{ride.rideStatus}</span>
                    </td>
                    <td>
                      <FaInfoCircle className="info-icon" onClick={() => handleDetailsClick(ride)} style={{ cursor: 'pointer', fontSize: "20px" }} />
                    </td>
                    <td>
                      <FaMoneyBillWave className="pay-icon" onClick={() => handlePayClick(ride)} style={{ cursor: 'pointer', color: 'green', fontSize: "20px" }} />
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </>
        )}
        {selectedRide && <RideCustomerModal ride={selectedRide} onClose={handleCloseModal} />}
      </div>
      {renderPagination()}
    </div>
  );
}

export default PendingPayments;
