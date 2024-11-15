import React from 'react';
import { Button } from 'react-bootstrap';
import '../../styles/CustomButton.css';

export default function CustomButton(props) {
  const { buttonTitle = 'Submit', buttonStyle = {}, textStyle = {}, ...rest } = props;
  return (
    <div className="custom-button-container" style={buttonStyle}>
      <Button className="custom-button" {...rest}>
        <span className="custom-button-text" style={textStyle}>{buttonTitle}</span>
      </Button>
    </div>
  );
}
