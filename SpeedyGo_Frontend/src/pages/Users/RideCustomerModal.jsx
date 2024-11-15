import React from 'react';
import { Modal, Button } from 'react-bootstrap';
import { RIDECUSTOMERMODAL_TITLES } from '../../constants';

function RideCustomerModal({ ride, onClose }) {
  if (!ride) return null;

  return (
    <Modal show={!!ride} onHide={onClose}>
      <Modal.Header closeButton>
        <Modal.Title>{RIDECUSTOMERMODAL_TITLES.MODAL_TITLE}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <p><strong>{RIDECUSTOMERMODAL_TITLES.REQUEST_ID}</strong> {ride.requestId}</p>
        <p><strong>{RIDECUSTOMERMODAL_TITLES.DISTANCE}</strong> {ride.distance}</p>
        <p><strong>{RIDECUSTOMERMODAL_TITLES.TOTAL_PRICE}</strong>₹{parseFloat(ride.totalPrice).toFixed(2)}</p>
        <p><strong>{RIDECUSTOMERMODAL_TITLES.TRANSPORTER_NAME}</strong> {ride.transporterInfo.username}</p>
        <p><strong>{RIDECUSTOMERMODAL_TITLES.TRANSPORTER_EMAIL}</strong> {ride.transporterInfo.email}</p>
        <p><strong>{RIDECUSTOMERMODAL_TITLES.PHONE_NUMBER}</strong> {ride.transporterInfo.phoneNumber}</p>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onClose}>
          {RIDECUSTOMERMODAL_TITLES.CLOSE_BUTTON}
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default RideCustomerModal;
