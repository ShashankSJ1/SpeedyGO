import React, { useState, useEffect } from 'react';
import { useFormik } from 'formik';
import { FaEye, FaEyeSlash } from 'react-icons/fa';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import CustomButton from '../Components/CustomComponents/CustomButton';
import CustomFormInput from '../Components/CustomComponents/CustomFormInput';
import '../styles/ForgotPassword.css';
import forgotpasswordimg from "../images/forgot.jpg"
import { FORGOTPASSWORD_MESSAGES, FORGOTPASSWORD_ENDPOINTS, FORGOTPASSWORD_VALIDATION_SCHEMA, FORGOTPASSWORD_UI_TEXT } from '../constants';

const ForgotPassword = () => {
  const [step, setStep] = useState(1); // Step 1: Email, Step 2: OTP, Step 3: Password reset
  const [message, setMessage] = useState(''); // Message for success
  const [errorMessage, setErrorMessage] = useState(''); // Message for errors
  const [otp, setOtp] = useState(['', '', '', '', '', '']); // Array to hold OTP digits
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [confirmshowPassword, setConfirmsetShowPassword] = useState(false);


  const toggleConfirmPasswordVisibility = () => {
    setConfirmsetShowPassword(!confirmshowPassword);
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };
  useEffect(() => {
    // Set focus to the first input box on component mount
    setTimeout(() => {
      const firstInput = document.getElementById('otp-0');
      if (firstInput) {
        firstInput.focus();
      }
    }, 0);
  }, []);

  const handleOtpChange = (value, index) => {
    let newOtp = [...otp];

    // Check if the input is a digit
    if (/^\d$/.test(value) && index < otp.length) {
      newOtp[index] = value; // Set the digit in the state
      setOtp(newOtp);

      // Move focus to the next input box if it exists
      if (index < 5) {
        document.getElementById(`otp-${index + 1}`).focus();
      }
    } else if (value === '') {
      // Handle deletion
      newOtp[index] = '';
      setOtp(newOtp);

      // Move focus to the previous input box if the current one is empty and not the first
      if (index > 0) {
        document.getElementById(`otp-${index - 1}`).focus();
      }
    }
  };

  const handleKeyDown = (event, index) => {
    if (event.key === 'Backspace') {
      if (!otp[index] && index > 0) {
        document.getElementById(`otp-${index - 1}`).focus();
      }
    }
  };
  const handleEmailSubmit = async (values) => {
    const emailCheckResponse = await axios.post(FORGOTPASSWORD_ENDPOINTS.EMAIL_PRESENT, null, {
      params: { email: values.email }
    });

    if (emailCheckResponse.data === "Email present") {
      try {
        const response = await axios.post(FORGOTPASSWORD_ENDPOINTS.OTP_GENERATE, null, {
          params: {
            email: values.email
          }
        });
        toast.success(response.data.message);
        setMessage(response.data.message);
        setErrorMessage('');
        setEmail(values.email); // Store the email in state
        setStep(2); // Proceed to Step 2
      } catch (error) {
        console.error(FORGOTPASSWORD_MESSAGES.OTP_GENERATION_ERROR, error);
        toast.error(FORGOTPASSWORD_MESSAGES.OTP_GENERATION_FAILED);
      }
    } else {
      emailFormik.setErrors({ 'email': FORGOTPASSWORD_MESSAGES.EMAIL_NOT_REGISTERED });
    }
  };

  const handleOtpSubmit = async () => {
    const enteredOtp = otp.join('');
    console.log("Email:", email); // Use the email from state
    console.log("Entered OTP:", enteredOtp);

    try {
      const response = await axios.post(FORGOTPASSWORD_ENDPOINTS.OTP_VALIDATE, null, {
        params: {
          email: email, // Use the email from state
          otp: enteredOtp
        }
      });
      toast.success(response.data.message);
      setStep(3);
    } catch (error) {
      console.error(FORGOTPASSWORD_MESSAGES.OTP_VALIDATION_ERROR, error);
      toast.error(FORGOTPASSWORD_MESSAGES.OTP_VALIDATION_FAILED);
    }
  };

  const handlePasswordSubmit = async (values) => {
    try {
      const response = await axios.patch(FORGOTPASSWORD_ENDPOINTS.RESET_PASSWORD, null, {
        params: {
          email: email,
          newPassword: values.newPassword
        }
      });
      toast.success(response.data.message);
      navigate('/');
    } catch (error) {
      console.error(FORGOTPASSWORD_MESSAGES.PASSWORD_RESET_ERROR, error);
      toast.error(FORGOTPASSWORD_MESSAGES.PASSWORD_RESET_FAILED);
    }
  };

  const emailFormik = useFormik({
    initialValues: { email: '' },
    validationSchema: FORGOTPASSWORD_VALIDATION_SCHEMA.EMAIL,
    onSubmit: handleEmailSubmit,
  });

  const passwordFormik = useFormik({
    initialValues: { newPassword: '', confirmPassword: '' },
    validationSchema: FORGOTPASSWORD_VALIDATION_SCHEMA.PASSWORD,
    onSubmit: handlePasswordSubmit,
  });

  return (
    <div className="forgot-password-container">
      <div className="forgot-password-form">
        <h2 className="websitename-forgotpassword">SPEEDY GO</h2>
        <img src={forgotpasswordimg} alt="logo" className="forgot-password-logo" />
        {message && <p className="success-message">{message}</p>}
        {errorMessage && <p className="error-message">{errorMessage}</p>}

        {step === 1 && (
          <>
            <h2>{FORGOTPASSWORD_UI_TEXT.FORGOT_PASSWORD_TITLE}</h2>
            <p>{FORGOTPASSWORD_UI_TEXT.ENTER_EMAIL_PROMPT}</p>
            <form onSubmit={emailFormik.handleSubmit}>
              <div className="input-group">
                <label htmlFor="email">{FORGOTPASSWORD_UI_TEXT.EMAIL_LABEL}</label>
                <CustomFormInput
                  tag="input"
                  name="email"
                  type="email"
                  placeholder={FORGOTPASSWORD_UI_TEXT.EMAIL_PLACEHOLDER}
                  value={emailFormik.values.email}
                  onChange={emailFormik.handleChange}
                  onBlur={emailFormik.handleBlur}
                  className={emailFormik.touched.email && emailFormik.errors.email ? 'input-error' : ''}
                />
                {emailFormik.touched.email && emailFormik.errors.email && (
                  <div className="error-message">{emailFormik.errors.email}</div>
                )}
              </div>
              <CustomButton type="submit" buttonTitle={FORGOTPASSWORD_UI_TEXT.SEND_OTP_BUTTON} />
            </form>
          </>
        )}

        {step === 2 && (
          <>
            <h2>{FORGOTPASSWORD_UI_TEXT.ENTER_OTP_TITLE}</h2>
            <p>{FORGOTPASSWORD_UI_TEXT.OTP_SENT_PROMPT}</p>
            <div className="otp-input-group">
              {otp.map((digit, index) => (
                <input
                  key={`${digit}-${index}`}
                  type="text"
                  id={`otp-${index}`}
                  maxLength="1"
                  value={digit}
                  onChange={(e) => handleOtpChange(e.target.value, index)}
                  onKeyDown={(e) => handleKeyDown(e, index)}
                  className="otp-input-box"
                />
              ))}
            </div>
            <CustomButton onClick={handleOtpSubmit} buttonTitle={FORGOTPASSWORD_UI_TEXT.VERIFY_OTP_BUTTON} />
          </>
        )}

        {step === 3 && (
          <>
            <h2>{FORGOTPASSWORD_UI_TEXT.RESET_PASSWORD_TITLE}</h2>
            <form onSubmit={passwordFormik.handleSubmit}>
              <div className="input-group">
                <label htmlFor="newPassword">{FORGOTPASSWORD_UI_TEXT.NEW_PASSWORD_LABEL}</label>
                <div className="input-wrapper">
                  <CustomFormInput
                    name="newPassword"
                    type={showPassword ? "text" : "password"}
                    placeholder={FORGOTPASSWORD_UI_TEXT.NEW_PASSWORD_PLACEHOLDER}
                    value={passwordFormik.values.newPassword}
                    onChange={passwordFormik.handleChange}
                    onBlur={passwordFormik.handleBlur}
                    className={passwordFormik.touched.newPassword && passwordFormik.errors.newPassword ? 'input-error' : ''}
                  />
                  <span className="eye-icon" onClick={togglePasswordVisibility}>
                    {showPassword ? <FaEyeSlash /> : <FaEye />}
                  </span>
                </div>
                {passwordFormik.touched.newPassword && passwordFormik.errors.newPassword && (
                  <div className="error-message">{passwordFormik.errors.newPassword}</div>
                )}
              </div>
              <div className="input-group">
                <label htmlFor="confirmPassword">{FORGOTPASSWORD_UI_TEXT.CONFIRM_PASSWORD_LABEL}</label>
                <div className="input-wrapper">
                  <CustomFormInput
                    name="confirmPassword"
                    type={confirmshowPassword ? "text" : "password"}
                    placeholder={FORGOTPASSWORD_UI_TEXT.CONFIRM_PASSWORD_PLACEHOLDER}
                    value={passwordFormik.values.confirmPassword}
                    onChange={passwordFormik.handleChange}
                    onBlur={passwordFormik.handleBlur}
                    className={passwordFormik.touched.confirmPassword && passwordFormik.errors.confirmPassword ? 'input-error' : ''}
                  />
                  <span className="eye-icon" onClick={toggleConfirmPasswordVisibility}>
                    {confirmshowPassword ? <FaEyeSlash /> : <FaEye />}
                  </span>
                </div>
                {passwordFormik.touched.confirmPassword && passwordFormik.errors.confirmPassword && (
                  <div className="error-message">{passwordFormik.errors.confirmPassword}</div>
                )}
              </div>
              <CustomButton type="submit" buttonTitle={FORGOTPASSWORD_UI_TEXT.RESET_PASSWORD_BUTTON} />
            </form>
          </>
        )}
      </div>
      <ToastContainer />
    </div>
  );
};

export default ForgotPassword;
