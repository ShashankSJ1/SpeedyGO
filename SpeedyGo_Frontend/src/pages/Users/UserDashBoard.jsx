import React, { useState, useEffect } from 'react';
import '../../styles/UserDashBoard.css';
import CurrentRideTableCustomer from './CurrentRideTableCustomer';
import HistoryTableCustomer from './HistoryTableCustomer';
import PendingPayments from './PendingPayments';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import Cookies from "js-cookie";
import { setUser } from "../../Redux/Reducer";
import { FaHistory, FaCar, FaSignOutAlt, FaMoneyBillWave } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';
import { USERDASHBOARD_TABS, USERDASHBOARD_BUTTON_TITLES, USERDASHBOARD_MESSAGES, USERDASHBOARD_ENDPOINTS } from '../../constants';

function UserDashBoard() {
  const [customerData, setCustomerData] = useState([]);
  const email = useSelector(state => state.user.email);
  const token = Cookies.get('token');
  const dispatch = useDispatch();
  const [activeTab, setActiveTab] = useState(USERDASHBOARD_TABS.CURRENT_RIDE);
  const navigate = useNavigate();

  useEffect(() => {
    if (email && token) {
      fetchCustomerDetails();
    }
  }, [email, token]);

  const fetchCustomerDetails = async () => {
    try {
      const response = await axios.get(`${USERDASHBOARD_ENDPOINTS.FETCH_CUSTOMER_DETAILS}${email}`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      console.log('Customer Data:', response.data);
      setCustomerData(response.data);
    } catch (error) {
      console.error(USERDASHBOARD_MESSAGES.ERROR_FETCHING_DATA, error.response ? error.response.data : error.message);
    }
  }

  const handleLogout = () => {
    dispatch(setUser({ email: "", name: "", role: "", phoneNumber: "" }));
    Cookies.remove('token');
    navigate("/");
  }

  return (
    <div className="dashboard-container">
      <div className="sidebar">
        <h2>{USERDASHBOARD_MESSAGES.DASHBOARD_TITLE}</h2>
        <div className="button-group">
          <button className="sidebar-button" onClick={() => setActiveTab(USERDASHBOARD_TABS.CURRENT_RIDE)}>
            <FaCar className="icon" /> {USERDASHBOARD_BUTTON_TITLES.CURRENT_RIDE}
          </button>
          <button className="sidebar-button" onClick={() => setActiveTab(USERDASHBOARD_TABS.PENDING_PAYMENTS)}>
            <FaMoneyBillWave className="icon" /> {USERDASHBOARD_BUTTON_TITLES.PENDING_PAYMENTS}
          </button>
          <button className="sidebar-button" onClick={() => setActiveTab(USERDASHBOARD_TABS.HISTORY)}>
            <FaHistory className="icon" /> {USERDASHBOARD_BUTTON_TITLES.HISTORY}
          </button>
          <button className="sidebar-button" onClick={handleLogout}>
            <FaSignOutAlt className="icon" /> {USERDASHBOARD_BUTTON_TITLES.LOGOUT}
          </button>
        </div>
      </div>

      <div className="content">
        {activeTab === USERDASHBOARD_TABS.CURRENT_RIDE && (
          <CurrentRideTableCustomer customerData={customerData} fetchCustomerDetails={fetchCustomerDetails} />
        )}
        {activeTab === USERDASHBOARD_TABS.PENDING_PAYMENTS && (
          <PendingPayments customerData={customerData} />
        )}
        {activeTab === USERDASHBOARD_TABS.HISTORY && (
          <HistoryTableCustomer />
        )}
      </div>
    </div>
  );
}

export default UserDashBoard;
