import React from 'react';
import { useFormik } from 'formik';
import { FaCar, FaTag, FaTruck, FaDollarSign, FaRoad } from 'react-icons/fa';
import CustomFormInput from './CustomComponents/CustomFormInput';
import CustomButton from './CustomComponents/CustomButton';
import axios from 'axios';
import Cookies from 'js-cookie';
import { useSelector } from 'react-redux';
import "../styles/Quotations.css";
import { quotationsInitialValues, quotationsValidationSchema, QUOTATIONS_ENDPOINTS, QUOTATIONS_UI_TEXT, QUOTATIONS_ERROR_MESSAGES, QUOTATIONS_SUCCESS_MESSAGES } from '../constants';
import Swal from 'sweetalert2';

function Quotations() {
  const email = useSelector(state => state.user.email);

  const formik = useFormik({
    initialValues: quotationsInitialValues,
    validationSchema: quotationsValidationSchema,
    onSubmit: async (values, { resetForm }) => {
      const data = {
        vehicleNumber: formik.values.vehicleNumber,
        vehicleName: formik.values.vehicleName,
        vehicleType: formik.values.vehicleType,
        basePrice: Number(formik.values.basePrice),
        pricePerKilometer: Number(formik.values.pricePerKm),
        status: "AVAILABLE",
        transporter: {
          email: email
        }
      };

      // Retrieve the token from cookies
      const token = Cookies.get('token');
      try {
        const response = await axios.post(QUOTATIONS_ENDPOINTS.ADD_VEHICLE, data, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        console.log(response.data);
        Swal.fire('Vehicle Added', QUOTATIONS_SUCCESS_MESSAGES.VEHICLE_ADDED, 'success');
        resetForm();
      } catch (error) {
        console.error(QUOTATIONS_ERROR_MESSAGES.ADD_VEHICLE_ERROR, error);
        if (error.response) {
          Swal.fire('Error', QUOTATIONS_ERROR_MESSAGES.VEHILCLE_ALREADY_EXISTS, 'error');
        }
      }
    }
  });

  return (
    <div className='quotation-form-container'>
      <form className="quotations-form" onSubmit={formik.handleSubmit}>
        <div className="quotations-header">
          <h2 className="quotations-title">{QUOTATIONS_UI_TEXT.FORM_TITLE}</h2>
          <p className="quotations-subtitle">{QUOTATIONS_UI_TEXT.FORM_SUBTITLE}</p>
        </div>
        <div className="quotations-con">
          <div className="quotations-form-group">
            <div className="quotations-input-icon">
              <FaCar className="quotations-icon" />
              <CustomFormInput
                tag="input"
                type="text"
                name="vehicleNumber"
                label={QUOTATIONS_UI_TEXT.VEHICLE_NUMBER_LABEL}
                placeholder={QUOTATIONS_UI_TEXT.VEHICLE_NUMBER_PLACEHOLDER}
                value={formik.values.vehicleNumber}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
              />
            </div>
            {formik.touched.vehicleNumber && formik.errors.vehicleNumber ? (
              <div className="quotations-error">{formik.errors.vehicleNumber}</div>
            ) : null}
          </div>

          <div className="quotations-form-group">
            <div className="quotations-input-icon">
              <FaTag className="quotations-icon" />
              <CustomFormInput
                tag="input"
                type="text"
                name="vehicleName"
                label={QUOTATIONS_UI_TEXT.VEHICLE_NAME_LABEL}
                placeholder={QUOTATIONS_UI_TEXT.VEHICLE_NAME_PLACEHOLDER}
                value={formik.values.vehicleName}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
              />
            </div>
            {formik.touched.vehicleName && formik.errors.vehicleName ? (
              <div className="quotations-error">{formik.errors.vehicleName}</div>
            ) : null}
          </div>

          <div className="quotations-form-group">
            <div className="quotations-input-icon">
              <FaTruck className="quotations-icon" />
              <CustomFormInput
                tag="input"
                type="text"
                name="vehicleType"
                label={QUOTATIONS_UI_TEXT.VEHICLE_TYPE_LABEL}
                placeholder={QUOTATIONS_UI_TEXT.VEHICLE_TYPE_PLACEHOLDER}
                value={formik.values.vehicleType}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
              />
            </div>
            {formik.touched.vehicleType && formik.errors.vehicleType ? (
              <div className="quotations-error">{formik.errors.vehicleType}</div>
            ) : null}
          </div>

          <div className="quotations-form-group">
            <div className="quotations-input-icon">
              <FaDollarSign className="quotations-icon" />
              <CustomFormInput
                tag="input"
                type="number"
                name="basePrice"
                label={QUOTATIONS_UI_TEXT.BASE_PRICE_LABEL}
                placeholder={QUOTATIONS_UI_TEXT.BASE_PRICE_PLACEHOLDER}
                value={formik.values.basePrice}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
              />
            </div>
            {formik.touched.basePrice && formik.errors.basePrice ? (
              <div className="quotations-error">{formik.errors.basePrice}</div>
            ) : null}
          </div>

          <div className="quotations-form-group">
            <div className="quotations-input-icon">
              <FaRoad className="quotations-icon" />
              <CustomFormInput
                tag="input"
                type="number"
                name="pricePerKm"
                label={QUOTATIONS_UI_TEXT.PRICE_PER_KM_LABEL}
                placeholder={QUOTATIONS_UI_TEXT.PRICE_PER_KM_PLACEHOLDER}
                value={formik.values.pricePerKm}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
                required
              />
            </div>
            {formik.touched.pricePerKm && formik.errors.pricePerKm ? (
              <div className="quotations-error">{formik.errors.pricePerKm}</div>
            ) : null}
          </div>

          <CustomButton type="submit" buttonTitle={QUOTATIONS_UI_TEXT.SUBMIT_BUTTON} />
        </div>
      </form>
    </div>
  );
}

export default Quotations;
