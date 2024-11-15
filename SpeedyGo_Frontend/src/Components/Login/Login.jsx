import React, { useState } from "react";
import { useFormik } from "formik";
import * as Yup from "yup";
import axios from "axios";
import "../../styles/LoginRegistration.css";
import { FaEnvelope, FaLock, FaArrowLeft, FaEye, FaEyeSlash } from "react-icons/fa";
import { useNavigate } from "react-router-dom"; 
import CustomFormInput from "../CustomComponents/CustomFormInput";
import CustomButton from "../CustomComponents/CustomButton";
import { useDispatch } from 'react-redux';
import { setUser } from "../../Redux/Reducer";
import Cookies from "js-cookie";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { loginValidationSchema, ENDPOINTS, ERROR_MESSAGES, UI_TEXT,customer,transporter,navigatecustomer,navigatetransporter } from '../../constants';
// Validation schema using Yup
function Login({ toggleSignUp }) {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [showPassword, setShowPassword] = useState(false);

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const formik = useFormik({
    initialValues: {
      email: "",
      password: "",
    },
    validationSchema: loginValidationSchema,
    onSubmit: async (values) => {
      const data = {
        email: values.email,
        password: values.password
      };


      try {
        const emailCheckResponse = await axios.post(ENDPOINTS.EMAIL_PRESENT, null, {
          params: { email: values.email }
        });

        console.log(emailCheckResponse.data)

        if (emailCheckResponse.data === ERROR_MESSAGES.EMAIL_NOT_FOUND) {
          toast.error(ERROR_MESSAGES.LOGIN_ERROR);
        } else {
          const response = await axios.post(ENDPOINTS.LOGIN, data);
          
          const email = response.data.email;
          const role = response.data.roles;
          const name = response.data.username;
          const token = response.data.token;
          const phoneNumber = response.data.phoneNumber;
          Cookies.set('token', token, { expires: 7 });
          // Dispatch user data to the store
          dispatch(setUser({ email, name, role, phoneNumber }));

          if (role === customer) {
            navigate(navigatecustomer);
            window.scrollTo(0, 0);
          } else if (role === transporter) {
            navigate(navigatetransporter);
            window.scrollTo(0, 0);
          }
        }
      } catch (error) {
        toast.error(ERROR_MESSAGES.LOGIN_ERROR);
      }
    }
  });

  return (
    <div className="signin">
      <nav>
        <ul className="signin-signup-ul">
          <li className="signin-signup-li">
            <a href="#"></a>
          </li>
          <li>
            {UI_TEXT.DONT_HAVE_ACCOUNT}
            <a href="javascript:void(0);" onClick={toggleSignUp}>
              <FaArrowLeft className="t-signup" />
            </a>
          </li>
        </ul>
      </nav>
      <div className="heading">
        <h2>{UI_TEXT.SIGNIN_HEADING}</h2>
        <p>{UI_TEXT.SIGNIN_SUBHEADING}</p>
      </div>
      <form onSubmit={formik.handleSubmit} className="signin-signup-form">
        {/* Email input field */}
        <p className="form-data">
          <FaEnvelope className="ri-mail-line" />
          <CustomFormInput
            type="text"
            name="email"
            placeholder={UI_TEXT.EMAIL_PLACEHOLDER}
            value={formik.values.email}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
          />
        </p>
        {formik.touched.email && formik.errors.email ? (
          <div className="error">{formik.errors.email}</div>
        ) : null}

        {/* Password input field */}
        <p className="form-data">
          <FaLock className="ri-lock-line" />
          <CustomFormInput
            type={showPassword ? "text" : "password"}
            name="password"
            placeholder={UI_TEXT.PASSWORD_PLACEHOLDER}
            onChange={formik.handleChange}
            onBlur={formik.handleBlur}
            value={formik.values.password}
          />
          <span className="eye-icon" onClick={togglePasswordVisibility}>
            {showPassword ? <FaEyeSlash /> : <FaEye />}
          </span>
        </p>
        {formik.touched.password && formik.errors.password ? (
          <div className="error">{formik.errors.password}</div>
        ) : null}

        {/* Forgot Password link */}
        <div className="forgot-password">
          <p onClick={() => navigate('/forgot-password')}>
            {UI_TEXT.FORGOT_PASSWORD}
          </p>
        </div>

        <CustomButton type="submit" buttonTitle={UI_TEXT.SIGNIN_BUTTON} />
      </form>
      <ToastContainer />
    </div>
  );
}

export default Login;