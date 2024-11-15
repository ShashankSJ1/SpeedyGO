import React from 'react';
import { useFormik } from 'formik';
import { FaMapMarkerAlt, FaPhoneAlt, FaEnvelope } from 'react-icons/fa';
import '../styles/Contact.css';
import { contactInitialValues, contactValidationSchema, CONTACT_INFO, CONTACT_UI_TEXT,CONTACT_ENDPOINT,CONTACT_ERROR } from '../constants';
import CustomFormInput from './CustomComponents/CustomFormInput';
import CustomButton from './CustomComponents/CustomButton';
import Cookies from 'js-cookie';
import axios from 'axios';
import Swal from 'sweetalert2';

const ContactForm = () => {
  const token = Cookies.get('token');
  const formik = useFormik({
    initialValues: contactInitialValues,
    validationSchema: contactValidationSchema,
    onSubmit: async (values) => {
      const data = {
        email:values.email,
        name:values.name,
        message:values.message
      }
      try {
        console.log(CONTACT_ENDPOINT)
        const response = await axios.post(CONTACT_ENDPOINT,data, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        })
        console.log(response);
        Swal.fire('Success', response.data, 'success')
      }
      catch(error){
        Swal.fire('Error',CONTACT_ERROR, 'error')
      }
    },
  });

  return (
    <div className="form-wrapper">
      <div className="contact-container">
        <div className="contact-content">
          <div className="left-side">
            <div className="address details">
              <FaMapMarkerAlt className="icon" />
              <div className="topic">{CONTACT_INFO.ADDRESS.TOPIC}</div>
              <div className="text-one">{CONTACT_INFO.ADDRESS.LINE1}</div>
              <div className="text-two">{CONTACT_INFO.ADDRESS.LINE2}</div>
            </div>
            <div className="phone details">
              <FaPhoneAlt className="icon" />
              <div className="topic">{CONTACT_INFO.PHONE.TOPIC}</div>
              <div className="text-one">{CONTACT_INFO.PHONE.NUMBER1}</div>
              <div className="text-two">{CONTACT_INFO.PHONE.NUMBER2}</div>
            </div>
            <div className="email details">
              <FaEnvelope className="icon" />
              <div className="topic">{CONTACT_INFO.EMAIL.TOPIC}</div>
              <div className="text-one">{CONTACT_INFO.EMAIL.EMAIL1}</div>
              <div className="text-two">{CONTACT_INFO.EMAIL.EMAIL2}</div>
            </div>
          </div>

          <div className="right-side">
            <div className="topic-text">{CONTACT_UI_TEXT.SEND_MESSAGE}</div>
            <p>{CONTACT_UI_TEXT.MESSAGE_PROMPT}</p>

            <form onSubmit={formik.handleSubmit}>
              <CustomFormInput
                tag="input"
                type="text"
                name="name"
                placeholder={CONTACT_UI_TEXT.NAME_PLACEHOLDER}
                label={CONTACT_UI_TEXT.NAME_LABEL}
                value={formik.values.name}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
              />
              {formik.touched.name && formik.errors.name && (
                <div className="error">{formik.errors.name}</div>
              )}

              <CustomFormInput
                tag="input"
                type="email"
                name="email"
                placeholder={CONTACT_UI_TEXT.EMAIL_PLACEHOLDER}
                label={CONTACT_UI_TEXT.EMAIL_LABEL}
                value={formik.values.email}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
              />
              {formik.touched.email && formik.errors.email && (
                <div className="error">{formik.errors.email}</div>
              )}

              <CustomFormInput
                tag="textarea"
                name="message"
                placeholder={CONTACT_UI_TEXT.MESSAGE_PLACEHOLDER}
                label={CONTACT_UI_TEXT.MESSAGE_LABEL}
                value={formik.values.message}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
                rows="5"
              />
              {formik.touched.message && formik.errors.message && (
                <div className="error">{formik.errors.message}</div>
              )}

              <CustomButton buttonTitle={CONTACT_UI_TEXT.SUBMIT_BUTTON} type="submit"/>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ContactForm;
