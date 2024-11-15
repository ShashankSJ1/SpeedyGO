import React from 'react';
import '../styles/UserStyles/CustomTransporterDetailsCard.css';
import CustomButton from './CustomComponents/CustomButton';

function CustomTransporterDetailsCard({ imgsrc, vehicleType, vehicleName, btntitle, openModal, ...optionalProps }) {
    const displayableProps = Object.entries(optionalProps).filter(
        ([key, value]) => typeof value !== 'function'
    );

    return (
        <div className='custom-transporter-card'>
            <div className="image-container">
                <img src={imgsrc} alt="Vehicle" className='card-img-top' />
            </div>
            <div className='card-body text-dark'>
                <h4 className="card-title">{vehicleName}</h4>
                <p className='card-text'>{vehicleType}</p>
                <div className="dynamic-props">
                    {displayableProps.map(([key, value]) => (
                        <div key={key} className="dynamic-item">
                            <h6 className='dynamic-heading'>{key}: </h6><span className="dynamic-value"> {value}</span>
                        </div>
                    ))}
                </div>
                <div className='button-container'>
                    <CustomButton
                        buttonTitle="View More"
                        data-bs-toggle="modal"
                        data-bs-target="#transporterModal"
                        onClick={openModal}
                    />
                    <CustomButton
                        buttonTitle={btntitle}
                        {...optionalProps}
                        style={{ display: 'flex', justifyContent: 'flex-start' }}
                    />
                </div>
            </div>
        </div>
    );
}
export default CustomTransporterDetailsCard;
