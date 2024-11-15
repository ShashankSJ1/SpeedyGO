import React from 'react';
// import '../../Styles/CustomCard.css';
import CustomButton from './CustomButton';

function CustomCard({ imgsrc, btntitle, cardheading, description, ...optionalProps }) {
    const displayableProps = Object.entries(optionalProps).filter(
        ([key, value]) => typeof value !== 'function'
    );

    return (
        <div className='custom-card'>
            <div className="overflow-container">
                <img src={imgsrc} alt="img" className='card-img-top' />
            </div>
            <div className='card-body text-dark'>
                <h4 className="card-title">{cardheading}</h4>
                <p className='card-text'>{description}</p>
                <div className="dynamic-props">
                    {displayableProps.map(([key, value]) => (
                        <div key={key} className="dynamic-item">
                            <strong style={{ color: "white" }}>{key}:</strong> <span className="dynamic-value">{value}</span>
                        </div>
                    ))}
                </div>

                <CustomButton 
                    buttonTitle={btntitle}
                    {...optionalProps} 
                />
            </div>
        </div>
    );
}

export default CustomCard;
