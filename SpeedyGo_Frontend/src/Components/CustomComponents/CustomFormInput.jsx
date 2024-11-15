import React from 'react';
import '../../styles/CustomFormInput.css';

export default function CustomFormInput({
  tag = 'input',
  type = 'text',
  options = [],
  label,
  ...rest 
}) {
  let element;

  switch (tag) {
    case 'input':
      element = <input type={type} {...rest} className="custom-input" />;
      break;

    case 'select':
      element = (
        <select {...rest} className="custom-select">
          {options.map((option, index) => (
            <option key={index} value={option.value}>
              {option.label}
            </option>
          ))}
        </select>
      );
      break;

    case 'textarea':
      element = <textarea {...rest} className="custom-textarea" />;
      break;

    default:
      element = React.createElement(tag, { ...rest, className: 'custom-element' });
      break;
  }

  return (
    <div className="custom-form-input">
      {label && <label className="custom-label">{label}</label>}
      {element}
    </div>
  );
}

