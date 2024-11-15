import React, { useState, useRef } from "react";
import { useFormik } from "formik";
import {FaEnvelope, FaLock, FaUser, FaArrowLeft, FaEyeSlash, FaEye, FaPhone } from "react-icons/fa";
import "../../styles/LoginRegistration.css";
import CustomButton from '../CustomComponents/CustomButton';
import CustomFormInput from "../CustomComponents/CustomFormInput";
import axios from "axios";
import Swal from 'sweetalert2';
import PasswordStrengthBar from 'react-password-strength-bar';
import PasswordChecklist from "react-password-checklist";
import { initialValues, validationSchema, ENDPOINTS, SWAL_MESSAGES, ERROR_MESSAGES, UI_TEXT } from '../../constants';

function Register({ toggleLogin }) {
  const [showOtpInput, setShowOtpInput] = useState(false);
  const otpRefs = useRef([]);
  const [password, setPassword] = useState('');
  const [isOtpButtonDisabled, setIsOtpButtonDisabled] = useState(false);
  const [otp, setOtp] = useState(Array(6).fill(''));
  const [showPassword, setShowPassword] = useState(false);
  const [confirmshowPassword, setConfirmsetShowPassword] = useState(false);

  const toggleConfirmPasswordVisibility = () => {
    setConfirmsetShowPassword(!confirmshowPassword);
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const formik = useFormik({
    initialValues,
    validationSchema,
    onSubmit: async (values) => {
      const data = {
        email: formik.values.email,
        username: formik.values.username,
        password: formik.values.password,
        phonenumber: formik.values.phoneno,
        roles: formik.values.role.toUpperCase()
      };
      console.log(formik.values.email)
      try {
        const emailCheckResponse = await axios.post(ENDPOINTS.EMAIL_PRESENT, null, {
          params: { email: values.email }
        });

        console.log(emailCheckResponse);

        if (emailCheckResponse.data === "Email present") {
          formik.setErrors({ email: SWAL_MESSAGES.EMAIL_EXISTS });
        } else {
          const response = await axios.post(ENDPOINTS.REGISTER, data, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          Swal.fire(SWAL_MESSAGES.SUCCESS, response.data, 'success').then((result) => {
            if (result.isConfirmed) {
              toggleLogin();
            }
          })
        }
      } catch (error) {
        console.log(ERROR_MESSAGES.EMAIL_CHECK_ERROR, error);
      }
    }
  });

  const handleSendOtp = async () => {
    await formik.validateField('email');
    if (!formik.errors.email && formik.values.email) {
      try {
         await axios.post(ENDPOINTS.OTP_GENERATE, null, {
          params: {
            email: formik.values.email
          }
        });
        Swal.fire(SWAL_MESSAGES.SUCCESS, SWAL_MESSAGES.OTP_SENT, 'success');
        setShowOtpInput(true);
        setIsOtpButtonDisabled(true);
        setTimeout(() => {
          setIsOtpButtonDisabled(false);
          setShowOtpInput(false);
        }, 60000);
      } catch (error) {
        console.error(ERROR_MESSAGES.OTP_GENERATE_ERROR, error);
        Swal.fire('Error', SWAL_MESSAGES.OTP_ERROR, 'error');
      }
    } else {
      formik.setFieldTouched('email', true);
    }
  };

  const handleOtpChange = (value, index) => {
    let newOtp = [...otp];

    if (/^\d$/.test(value) && index < otp.length) {
      newOtp[index] = value;
      setOtp(newOtp);
      formik.setFieldValue("otp", newOtp.join(""));

      if (index < 5) {
        document.getElementById(`otp-${index + 1}`).focus();
      }
    } else if (value === '') {
      newOtp[index] = '';
      setOtp(newOtp);
      formik.setFieldValue("otp", newOtp.join(""));

      if (index > 0) {
        document.getElementById(`otp-${index - 1}`).focus();
      }
    }
  };

  const handleOtpKeyDown = (event, index) => {
    if (event.key === 'Backspace') {
      if (!otp[index] && index > 0) {
        document.getElementById(`otp-${index - 1}`).focus();
      }
    }
  };

  const handleOtpSubmit = async () => {
    const enteredOtp = otp.join('');
    console.log(enteredOtp);
    try {
      const response = await axios.post(ENDPOINTS.OTP_VALIDATE, null, {
        params: {
          email: formik.values.email,
          otp: enteredOtp
        }
      });
      Swal.fire(SWAL_MESSAGES.SUCCESS, response.data.message, 'success');
      setShowOtpInput(!showOtpInput);
    } catch (error) {
      console.error(ERROR_MESSAGES.OTP_VALIDATE_ERROR, error);
      Swal.fire('Error', SWAL_MESSAGES.OTP_ERROR, 'error');
    }
  };
  return (
    <div className="signup">
      <nav>
        <ul>
          <li>
            <a href="#"></a>
          </li>
          <li>
            {UI_TEXT.ALREADY_MEMBER}
            <a href="javascript:void(0);" onClick={toggleLogin}>
              <FaArrowLeft className="t-signin" />
            </a>
          </li>
        </ul>
      </nav>
      <div className="heading">
        <h2>{UI_TEXT.SIGNUP_HEADING}</h2>
        <p>{UI_TEXT.SIGNUP_SUBHEADING}</p>
      </div>
      <form onSubmit={formik.handleSubmit}>
        <p className="form-data">
          <label htmlFor="role">{UI_TEXT.ROLE_LABEL}</label>
          <select
            id="role"
            name="role"
            value={formik.values.role}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
          >
            <option value="">{UI_TEXT.ROLE_PLACEHOLDER}</option>
            <option value="customer">{UI_TEXT.ROLE_CUSTOMER}</option>
            <option value="transporter">{UI_TEXT.ROLE_TRANSPORTER}</option>
          </select>
        </p>
        {formik.touched.role && formik.errors.role ? (
          <div className="error">{formik.errors.role}</div>
        ) : null}
        <p className="form-data">
          <FaUser className="ri-user-3-line" />
          <CustomFormInput
            type="text"
            name="username"
            placeholder={UI_TEXT.USERNAME_PLACEHOLDER}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            value={formik.values.username}
          />
        </p>
        {formik.touched.username && formik.errors.username ? (
          <div className="error">{formik.errors.username}</div>
        ) : null}

        <p className="form-data">
          <FaPhone className="ri-user-3-line" />
          <CustomFormInput
            type="number"
            name="phoneno"
            placeholder={UI_TEXT.PHONE_PLACEHOLDER}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            value={formik.values.phoneno}
            maxLength={10}
          />
        </p>
        {formik.touched.phoneno && formik.errors.phoneno ? (
          <div className="error">{formik.errors.phoneno}</div>
        ) : null}
        <p className="form-data">
          <FaEnvelope className="ri-mail-line" />
          <CustomFormInput
            type="text"
            name="email"
            placeholder={UI_TEXT.EMAIL_PLACEHOLDER}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            value={formik.values.email}
          />
        </p>
        {formik.touched.email && formik.errors.email ? (
          <div className="error">{formik.errors.email}</div>
        ) : null}
        <CustomButton buttonTitle={UI_TEXT.SEND_OTP_BUTTON} onClick={handleSendOtp} disabled={isOtpButtonDisabled} />
        {showOtpInput && (
          <div className="email-otp-input">
            {otp.map((value, index) => (
              <CustomFormInput
                key={index}
                id={`otp-${index}`}
                type="text"
                maxLength="1"
                value={value}
                onChange={(e) => handleOtpChange(e.target.value, index)}
                onKeyDown={(e) => handleOtpKeyDown(e, index)}
                ref={(el) => (otpRefs.current[index] = el)}
              />
            ))}
          </div>
        )}
        {formik.touched.otp && formik.errors.otp ? (
          <div className="error">{formik.errors.otp}</div>
        ) : null}
        {showOtpInput && <CustomButton buttonTitle={UI_TEXT.VERIFY_OTP_BUTTON} onClick={handleOtpSubmit} />}
        {/* Password input */}
        <p className="form-data">
          <FaLock className="ri-lock-line" />
          <CustomFormInput
            type={showPassword ? "text" : "password"}
            name="password"
            placeholder={UI_TEXT.PASSWORD_PLACEHOLDER}
            value={formik.values.password}
            onChange={(e) => {
              formik.handleChange(e);
              setPassword(e.target.value);
            }}
            onBlur={formik.handleBlur}
          />
          <span className="eye-icon" onClick={togglePasswordVisibility}>
            {showPassword ? <FaEyeSlash /> : <FaEye />}
          </span>
        </p>
        <PasswordStrengthBar password={password} />
        <PasswordChecklist
          rules={["minLength", "specialChar", "number", "capital", "match"]}
          minLength={8}
          value={password}
          valueAgain={formik.values.confirmPassword}
          onChange={(isValid) => { }}
        />

        {/* Confirm Password input */}
        <p className="form-data">
          <FaLock className="ri-lock-line" />
          <CustomFormInput
            type={confirmshowPassword ? "text" : "password"}
            name="confirmPassword"
            placeholder={UI_TEXT.CONFIRM_PASSWORD_PLACEHOLDER}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            value={formik.values.confirmPassword}
          />
          <span className="eye-icon" onClick={toggleConfirmPasswordVisibility}>
            {confirmshowPassword ? <FaEyeSlash /> : <FaEye />}
          </span>
        </p>
        {formik.touched.confirmPassword && formik.errors.confirmPassword ? (
          <div className="error">{formik.errors.confirmPassword}</div>
        ) : null}

        <CustomButton type="submit" buttonTitle={UI_TEXT.SIGNUP_BUTTON} />
      </form>
    </div>
  );
}

export default Register;
