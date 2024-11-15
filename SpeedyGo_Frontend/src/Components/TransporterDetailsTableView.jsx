import React from 'react';
import "../styles/UserStyles/TransporterDetails.css";
import { TRANSPORTERDETAILSTABLE_HEADERS, TRANSPORTERDETAILSTABLE_MESSAGES } from '../constants';
import { FaInfoCircle, FaPaperPlane } from 'react-icons/fa';

function TransporterDetailsTableView({ transporterData, openModal, submitRequest }) {
    return (
        <div className='TransporterTableView-table-responsive'>
            <table className="TransporterTableView-table">
                <thead>
                    <tr>
                        <th>{TRANSPORTERDETAILSTABLE_HEADERS.VEHICLE_NAME}</th>
                        <th>{TRANSPORTERDETAILSTABLE_HEADERS.VEHICLE_TYPE}</th>
                        <th>{TRANSPORTERDETAILSTABLE_HEADERS.BASE_PRICE}</th>
                        <th>{TRANSPORTERDETAILSTABLE_HEADERS.PRICE_PER_KM}</th>
                        <th>{TRANSPORTERDETAILSTABLE_HEADERS.TOTAL_PRICE}</th>
                        <th>{TRANSPORTERDETAILSTABLE_HEADERS.ACTION}</th>
                        <th>{TRANSPORTERDETAILSTABLE_HEADERS.REQUEST}</th>
                    </tr>
                </thead>
                <tbody>
                    {transporterData.map((item, index) => (
                        item.vehicleInfo ? (
                            <tr key={index}>
                                <td>{item.vehicleInfo.vehicleName}</td>
                                <td>{item.vehicleInfo.vehicleType}</td>
                                <td>₹{item.vehicleInfo.basePrice}</td>
                                <td>₹{item.vehicleInfo.pricePerKilometer}/km</td>
                                <td>₹{parseFloat(item.vehicleInfo.totalPrice).toFixed(2)}</td>
                                <td>
                                    <FaInfoCircle
                                        className="info-icon"
                                        style={{ cursor: 'pointer', fontSize: "20px" }}
                                        onClick={() => openModal(item)}
                                        data-bs-toggle="modal"
                                        data-bs-target="#transporterModal"
                                    />
                                </td>
                                <td>
                                    <FaPaperPlane
                                        className="request-icon"
                                        style={{ cursor: 'pointer', fontSize: "20px", color: 'green' }}
                                        onClick={() => submitRequest(item.vehicleInfo.totalPrice, item.transporterInfo.email, item.vehicleInfo.vehicleNumber)}
                                    />
                                </td>
                            </tr>
                        ) : (
                            <tr key={index}>
                                <td colSpan="7">{TRANSPORTERDETAILSTABLE_MESSAGES.VEHICLE_INFO_NOT_AVAILABLE}</td>
                            </tr>
                        )
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default TransporterDetailsTableView;
