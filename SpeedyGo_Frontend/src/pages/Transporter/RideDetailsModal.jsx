import React from 'react';
import { Modal, Button } from 'react-bootstrap';
import { RIDEDETAILSMODAL_TITLES } from '../../constants';

function RideDetailsModal({ show, handleClose, ride }) {
  if (!ride) return null;

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>{RIDEDETAILSMODAL_TITLES.MODAL_TITLE}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <p><strong>{RIDEDETAILSMODAL_TITLES.CUSTOMER_NAME}</strong> {ride.customerInfo.name}</p>
        <p><strong>{RIDEDETAILSMODAL_TITLES.EMAIL}</strong> {ride.customerInfo.email}</p>
        <p><strong>{RIDEDETAILSMODAL_TITLES.PHONE_NUMBER}</strong> {ride.customerInfo.phoneNumber}</p>
        <p><strong>{RIDEDETAILSMODAL_TITLES.TOTAL_PRICE}</strong>₹{parseFloat(ride.totalPrice).toFixed(2)}</p>
        <p><strong>{RIDEDETAILSMODAL_TITLES.RIDE_DATE}</strong> {ride.rideDate}</p>
        <p><strong>{RIDEDETAILSMODAL_TITLES.RIDE_TIME}</strong> {ride.rideTime}</p>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          {RIDEDETAILSMODAL_TITLES.CLOSE_BUTTON}
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default RideDetailsModal;
