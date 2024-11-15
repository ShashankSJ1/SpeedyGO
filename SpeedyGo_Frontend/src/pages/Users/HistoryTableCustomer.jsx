import React, { useState, useEffect } from 'react';
import CustomButton from '../../Components/CustomComponents/CustomButton';
import RideDetailsModal from '../Transporter/RideDetailsModal';
import { useSelector } from 'react-redux';
import Cookies from 'js-cookie';
import axios from 'axios';
import { HISTORYTABLECUSTOMER_HEADERS, HISTORYTABLECUSTOMER_MESSAGES, HISTORYTABLECUSTOMER_ENDPOINTS } from '../../constants';
import { FaInfoCircle } from 'react-icons/fa';
import { Pagination } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../../styles/UserDashBoard.css';

function HistoryTableCustomer() {
    const [historyData, setHistoryData] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 5;
    const email = useSelector(state => state.user.email);
    const [showModal, setShowModal] = useState(false);
    const [selectedRide, setSelectedRide] = useState(null);
    const token = Cookies.get('token');

    const handleShowModal = (ride) => {
        setSelectedRide(ride);
        setShowModal(true);
    };

    const handleCloseModal = () => {
        setShowModal(false);
        setSelectedRide(null);
    };

    useEffect(() => {
        const fetchHistoryData = async () => {
            try {
                const response = await axios.get(`${HISTORYTABLECUSTOMER_ENDPOINTS.FETCH_HISTORY}${email}`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                console.log(response.data);
                setHistoryData(response.data);
            } catch (error) {
                console.error(HISTORYTABLECUSTOMER_MESSAGES.ERROR_FETCHING_DATA, error);
            }
        };

        if (email) {
            fetchHistoryData();
        }
    }, [email, token]);

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentItems = historyData.slice(indexOfFirstItem, indexOfLastItem);

    const renderPagination = () => {
        const pageNumbers = [];
        for (let i = 1; i <= Math.ceil(historyData.length / itemsPerPage); i++) {
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
            <h2 className="text-center custom-margin-bottom">{HISTORYTABLECUSTOMER_MESSAGES.TITLE}</h2>
            <div className="table table-bordered table-responsive">
                {historyData.length === 0 ? (
                    <p className="text-center fw-bold my-2">{HISTORYTABLECUSTOMER_MESSAGES.NO_DATA}</p>
                ) : (
                    <>
                        <table>
                            <thead>
                                <tr>
                                    <th>{HISTORYTABLECUSTOMER_HEADERS.REQUEST_ID}</th>
                                    <th>{HISTORYTABLECUSTOMER_HEADERS.SOURCE}</th>
                                    <th>{HISTORYTABLECUSTOMER_HEADERS.DESTINATION}</th>
                                    <th>{HISTORYTABLECUSTOMER_HEADERS.STATUS}</th>
                                    <th>{HISTORYTABLECUSTOMER_HEADERS.DETAILS}</th>
                                </tr>
                            </thead>
                            <tbody>
                                {currentItems.map((ride) => (
                                    <tr key={ride.requestId}>
                                        <td>{ride.requestId}</td>
                                        <td>{ride.source}</td>
                                        <td>{ride.destination}</td>
                                        <td>{ride.rideStatus}</td>
                                        <td>
                                            <FaInfoCircle className="info-icon" style={{ cursor: 'pointer', fontSize: "20px" }} onClick={() => handleShowModal(ride)} />
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </>
                )}
                <RideDetailsModal show={showModal} handleClose={handleCloseModal} ride={selectedRide} />
            </div>
            {renderPagination()}
        </div>
    );
}

export default HistoryTableCustomer;
