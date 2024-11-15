import { FaTruckMoving, FaDollarSign, FaHandsHelping, FaShieldAlt, FaFacebook, FaTwitter, FaInstagram, FaLinkedin } from "react-icons/fa";

import * as Yup from 'yup';

//About
export const aboutset = [
  [<FaTruckMoving size="4rem" />, "Efficient Transport", "Reliable local transporters for seamless shifting"],
  [<FaDollarSign size="4rem" />, "Cost-Effective", "Compare quotes and find the best prices for your move"],
  [<FaHandsHelping size="4rem" />, "Supportive Service", "Dedicated assistance tailored to your moving needs"],
  [<FaShieldAlt size="4rem" />, "Secure Process", "Ensuring safety and security in every step of your move"],
];

export const topTexts = [
  "FIND THE BEST",
  "LOCAL TRANSPORTERS",
  "FOR YOUR MOVE",
];

export const commitmentText = "Our Commitments:";

export const belowTitles = [
  "Streamlined Transport Services",
  "Competitive Pricing and Quotes",
  "Trusted and Verified Transporters",
  "Hassle-Free Moving Experience",
  "Secure and Transparent Transactions",
  "Efficient and Fast Booking Process",
];

export const bottomBoldText = "Experience seamless relocation with SpeedyGo, connecting you with reliable local transporters.";
export const bottomServicesText = "Our services include:";


//Register and Login
export const initialValues = {
  role: "",
  username: "",
  email: "",
  phoneno: "",
  password: "",
  confirmPassword: "",
  otp: "",
};

export const validationSchema = Yup.object({
  role: Yup.string().required("Role is required"),
  username: Yup.string()
    .min(3, "Username must be at least 3 characters")
    .required("Username is required"),
  email: Yup.string()
    .email("Invalid email address")
    .required("Email is required"),
  password: Yup.string()
    .required("Password is required"),
  confirmPassword: Yup.string()
    .oneOf([Yup.ref("password"), null], "Passwords must match")
    .required("Confirm your password"),
  otp: Yup.string()
    .matches(/^\d+$/, "OTP must be a digit")
    .required("OTP is required"),
    phoneno: Yup.string()
    .matches(/^[1-9]\d*$/, "Phone number should be a positive number and should not start with 0")
    .min(10, "Phone number must be exactly 10 digits")
    .max(10, "Phone number must be exactly 10 digits")
    .required("Phone number is required"),  
});

export const loginValidationSchema = Yup.object({
  email: Yup.string()
    .email("Invalid email format")
    .required("Email is required"),
  password: Yup.string()
    .min(8, "Password must be at least 8 characters")
    .required("Password is required"),
});

export const mapValidationSchema = Yup.object({
  source: Yup.string().required('Source is required'),
  destination: Yup.string().required('Destination is required'),
  date: Yup.date().required('Date is required').nullable(),
  time: Yup.date().required('Time is required').nullable(),
});

export const ENDPOINTS = {
  EMAIL_PRESENT: "http://localhost:8091/emailPresent",
  REGISTER: "http://localhost:8091/register",
  OTP_GENERATE: "http://localhost:8091/otp/generate",
  OTP_VALIDATE: "http://localhost:8091/otp/validate",
  LOGIN: "http://localhost:8091/login",
  AVAILABLE_BOOKINGS: "http://localhost:8091/api/bookings/available",
  NOMINATIM_SEARCH: "https://nominatim.openstreetmap.org/search?format=json&q="
};

export const SWAL_MESSAGES = {
  SUCCESS: 'Success',
  EMAIL_EXISTS: 'Email already Exists',
  OTP_SENT: 'OTP sent to your email successfully',
  OTP_ERROR: 'Failed to send OTP.',
  OTP_SUCCESS: 'OTP validated successfully',
  ROUTE_ERROR: 'Failed to calculate the route. Please check the input addresses.',
  SOURCE_INVALID: 'Source location is invalid. Please try again.',
  DESTINATION_INVALID: 'Destination location is invalid. Please try again.',
  BOTH_INVALID: 'Both source and destination are invalid. Please try again.'
};

export const ERROR_MESSAGES = {
  EMAIL_CHECK_ERROR: "There was an error checking the email:",
  OTP_GENERATE_ERROR: "There was an error generating the OTP:",
  OTP_VALIDATE_ERROR: "There was an error validating the OTP:",
  LOGIN_ERROR: "Email or Password is incorrect",
  FETCH_COORDINATES_ERROR: "Error fetching coordinates:",
  LOCATION_NOT_FOUND: "Location not found",
  FETCH_BOOKINGS_ERROR: "Error fetching available bookings:"
};

export const UI_TEXT = {
  SIGNUP_HEADING: "Sign Up",
  SIGNUP_SUBHEADING: "We secure your data privacy encrypted.",
  ROLE_LABEL: "Role:",
  ROLE_PLACEHOLDER: "Select your role",
  ROLE_CUSTOMER: "CUSTOMER",
  ROLE_TRANSPORTER: "TRANSPORTER",
  USERNAME_PLACEHOLDER: "Enter your username",
  PHONE_PLACEHOLDER: "Enter your Phone number",
  EMAIL_PLACEHOLDER: "Enter your email",
  SEND_OTP_BUTTON: "Send OTP",
  VERIFY_OTP_BUTTON: "Verify OTP",
  PASSWORD_PLACEHOLDER: "Enter your password",
  CONFIRM_PASSWORD_PLACEHOLDER: "Confirm your password",
  SIGNUP_BUTTON: "Sign Up",
  ALREADY_MEMBER: "Already a member?",
  OR_TEXT: "Or",
  SIGNIN_HEADING: "Sign In",
  SIGNIN_SUBHEADING: "We secure your data privacy encrypted.",
  SIGNIN_BUTTON: "Sign In",
  DONT_HAVE_ACCOUNT: "Don't have an account?",
  FORGOT_PASSWORD: "Forgot Password?",
  SOURCE_PLACEHOLDER: "Enter your location",
  DESTINATION_PLACEHOLDER: "Enter logistics delivery location",
  DATE_PLACEHOLDER: "Select Date",
  TIME_PLACEHOLDER: "Select Time",
  CALCULATE_ROUTE_BUTTON: "Get Distance",
  GET_QUOTATION_BUTTON: "Search Riders",
  SCHEDULE_RIDE_BUTTON: "Schedule Ride",
  BACK_BUTTON: "Back",
  APP_TITLE: "Speedygo Your Moving Companion",
  EMAIL_NOT_FOUND: "Email not found",
};

//role 
export const customer="CUSTOMER";
export const transporter="TRANSPORTER";


//navigate
export const navigatecustomer="/user/home";
export const navigatetransporter="/transporter/home";
export const transporterProfile="/user/transporterprofile"

//contact
export const contactInitialValues = {
  name: '',
  email: '',
  message: '',
};

export const CONTACT_ENDPOINT="http://localhost:8091/api/contact/submit";

export const CONTACT_ERROR="Something went wrong";

export const contactValidationSchema = Yup.object({
  name: Yup.string()
    .required('Name is required'),
  email: Yup.string()
    .email('Invalid email format')
    .required('Email is required'),
  message: Yup.string()
    .min(10, 'Message must be at least 10 characters long')
    .required('Message is required'),
});

export const CONTACT_INFO = {
  ADDRESS: {
    TOPIC: "Address",
    LINE1: "Electronic city",
    LINE2: "Bengaluru"
  },
  PHONE: {
    TOPIC: "Phone",
    NUMBER1: "+9112 3456 7890",
    NUMBER2: "+0098 7654 3210"
  },
  EMAIL: {
    TOPIC: "Email",
    EMAIL1: "speedygo@gmail.com",
    EMAIL2: "speedy.go@gmail.com"
  }
};

export const CONTACT_UI_TEXT = {
  SEND_MESSAGE: "Send us a message",
  MESSAGE_PROMPT: "If you have any questions or need assistance related to SpeedyGo, feel free to send us a message. We're here to help you find the most cost-effective local transporters for your needs.",
  NAME_PLACEHOLDER: "Enter your name",
  EMAIL_PLACEHOLDER: "Enter your email",
  MESSAGE_PLACEHOLDER: "Enter your message",
  NAME_LABEL: "Name",
  EMAIL_LABEL: "Email",
  MESSAGE_LABEL: "Message"
};

//quotations
export const quotationsInitialValues = {
  vehicleNumber: '',
  vehicleName: '',
  basePrice: '',
  vehicleType: '',
  pricePerKm: '',
};

export const quotationsValidationSchema = Yup.object({
  vehicleNumber: Yup.string().required('Vehicle Number is required'),
  vehicleName: Yup.string().required('Vehicle Name is required'),
  vehicleType: Yup.string().required('Vehicle Type is required'),
  pricePerKm: Yup.number().required('Price per Km is required').positive('Price per Km must be positive'),
  basePrice: Yup.number().required('Base Price is Required').positive('Base Price must be positive'),
});

export const QUOTATIONS_ENDPOINTS = {
  ADD_VEHICLE: "http://localhost:8091/api/bookings/addVehicle"
};

export const QUOTATIONS_UI_TEXT = {
  FORM_TITLE: "Register Your Vehicle with SpeedyGo",
  FORM_SUBTITLE: "Please fill in the details below",
  VEHICLE_NUMBER_LABEL: "Vehicle Number",
  VEHICLE_NUMBER_PLACEHOLDER: "Enter the Vehicle Number",
  VEHICLE_NAME_LABEL: "Vehicle Name",
  VEHICLE_NAME_PLACEHOLDER: "Enter the Vehicle Name",
  VEHICLE_TYPE_LABEL: "Vehicle Type",
  VEHICLE_TYPE_PLACEHOLDER: "Enter the Vehicle Type",
  BASE_PRICE_LABEL: "Base Price",
  BASE_PRICE_PLACEHOLDER: "Enter the Base Price",
  PRICE_PER_KM_LABEL: "Price per Km",
  PRICE_PER_KM_PLACEHOLDER: "Enter the Price Per Kilometer",
  SUBMIT_BUTTON: "Submit Quotation"
};

export const QUOTATIONS_ERROR_MESSAGES = {
  ADD_VEHICLE_ERROR: "Error adding vehicle",
  VEHILCLE_ALREADY_EXISTS: "Vehicle already exists"
};

export const QUOTATIONS_SUCCESS_MESSAGES = {
  VEHICLE_ADDED: "Vehicle Added Successfully"
};


//transporterdetails
export const TRANSPOTDETAILS_INITIAL_FILTERS = {
  vehicleType: '',
  baseRate: '',
  pricePerKm: '',
};

export const TRANSPOTDETAILS_ITEMS_PER_PAGE = 3;

export const TRANSPOTDETAILS_ENDPOINTS = {
  SEARCH: "http://localhost:8091/api/bookings/search",
  FILTER: "http://localhost:8091/api/bookings/filter",
  REQUEST_RIDE: "http://localhost:8091/api/bookings/request-ride"
};

export const TRANSPOTDETAILS_MESSAGES = {
  SUCCESS: "Request Sent Successfully",
  ERROR: "Something went wrong",
  FETCH_ERROR: "Error fetching data:",
  RESPONSE_ERROR: "Error Response Data:",
  RESPONSE_STATUS: "Error Response Status:",
  RESPONSE_HEADERS: "Error Response Headers:",
  SWAL_TITLE:"Are you sure?",
  SWAL_TEXT:"Do you want to send this request?",
  SWAL_WARNING:"Warning",
  SWAL_CONFIRM:"Yes, send it!",
  SWAL_CANCEL:"No, cancel!",
  SWAL_CATCH_CANCEL:"Your request was not sent"
};

export const TRANSPOTDETAILS_UI_TEXT = {
  SEARCH_PLACEHOLDER: "Search here for transporters",
  SWITCH_TO_CARD_VIEW: "Switch to Card View",
  SWITCH_TO_TABLE_VIEW: "Switch to Table View",
  REQUEST_QUOTATIONS: "Request Quotations",
  VEHICLE_INFO_NOT_AVAILABLE: "Vehicle information is not available",
  MODAL_TITLE: "Transporter Details",
  MODAL_CLOSE: "Close",
  TRANSPORTER_NAME: "Transporter Name:",
  EMAIL: "Email:",
  PHONE_NUMBER: "Phone Number:",
  VEHICLE_NUMBER: "Vehicle Number:",
  VEHICLE_NAME: "Vehicle Name:",
  VEHICLE_TYPE: "Vehicle Type:",
  BASE_PRICE: "Base Price:",
  PRICE_PER_KM: "Price per Km:",
  TOTAL_PRICE: "Total Price:"
};


//transporterdetailstableview

export const TRANSPORTERDETAILSTABLE_HEADERS = {
  VEHICLE_NAME: "Vehicle Name",
  VEHICLE_TYPE: "Vehicle Type",
  BASE_PRICE: "Base Price",
  PRICE_PER_KM: "Price per Km",
  TOTAL_PRICE: "Total Price",
  ACTION: "Action",
  REQUEST: "Request"
};

export const TRANSPORTERDETAILSTABLE_BUTTON_TITLES = {
  DETAILS: "Details",
  REQUEST: "Request"
};

export const TRANSPORTERDETAILSTABLE_MESSAGES = {
  VEHICLE_INFO_NOT_AVAILABLE: "Vehicle information is not available"
};

//TransporterFilter
export const TRANSPORTERFILTER_LABELS = {
  FILTERS: "Filters :",
  VEHICLE_TYPE: "Vehicle Type",
  BASE_RATE: "Base Rate",
  PRICE_PER_KM: "Price Per Km",
  LOWEST: "Lowest",
  HIGHEST: "Highest"
};

export const TRANSPORTERFILTER_BUTTON_TITLES = {
  REMOVE_FILTER: "Remove Filter",
  APPLY_FILTER: "Apply Filter"
};

export const TRANSPORTERFILTER_OPTIONS = {
  SELECT_OPTION: { value: '', label: 'Select an option' }
};


//FOOTER
export const FOOTER_TEXT = {
  LOGO: "SpeedyGo",
  DESCRIPTION: "SpeedyGo is a platform for local transporters to register and provide service quotes. Customers can compare and choose the best transporters for their needs, making home or office shifting more affordable.",
  QUICK_LINKS: "Quick Links",
  CONTACT_US: "Contact Us",
  PHONE: "+91 1234567890",
  EMAIL: "speedygo@gmail.com",
  ADDRESS: "Electronic city, Bangalore."
};

export const FOOTER_LINKS = {
  LOGISTICS: "/user/logistics",
  QUOTATIONS: "/transporter/profile-management",
  CONTACT: "/user/contact",
  ABOUT: "/user/about",
  DASHBOARD: "/user/dashboard"
};

export const FOOTER_ICONS = {
  FACEBOOK: <FaFacebook size={24} className="footer-icon" />,
  TWITTER: <FaTwitter size={24} className="footer-icon" />,
  INSTAGRAM: <FaInstagram size={24} className="footer-icon" />,
  LINKEDIN: <FaLinkedin size={24} className="footer-icon" />
};

//HEADER
export const HEADER_ROUTES = {
  USER_HOME: "/user/home",
  TRANSPORTER_HOME: "/transporter/home",
  USER_LOGISTICS: "/user/logistics",
  USER_ABOUT: "/user/about",
  USER_CONTACT: "/user/contact",
  USER_DASHBOARD: "/user/dashboard",
  TRANSPORTER_PROFILE_MANAGEMENT: "/transporter/profile-management",
  TRANSPORTER_ABOUT: "/transporter/about",
  TRANSPORTER_CONTACT: "/transporter/contact",
  TRANSPORTER_DASHBOARD: "/transporter/dashboard"
};

export const HEADER_TEXT = {
  SPEEDY_GO: "SPEEDY GO",
  HOME: "HOME",
  LOGISTICS: "LOGISTICS",
  ABOUT_US: "ABOUT US",
  CONTACT: "CONTACT",
  DASHBOARD: "DASHBOARD",
  QUOTATIONS: "QUOTATIONS"
};

//CurrentRideTableTransporter

export const CURRENTRIDETABLETRANSPORTER_HEADERS = {
  CURRENT_RIDES:"Current Rides and Pending Requests",
  REQUEST_ID: "Request Id",
  SOURCE: "Source",
  DESTINATION: "Destination",
  STATUS: "Status",
  DETAILS: "Details",
  OTHER: "Other"
};

export const CURRENTRIDETABLETRANSPORTER_BUTTON_TITLES = {
  DETAILS: "Details",
  END_RIDE: "End-Ride",
  ACCEPT: "Accept",
  CANCEL: "Cancel"
};

export const CURRENTRIDETABLETRANSPORTER_MESSAGES = {
  NO_PENDING_REQUEST: "No pending Request",
  ACCEPT_REQUEST_TITLE: "Are you sure",
  ACCEPT_REQUEST_TEXT: "Accept Request",
  ACCEPT_REQUEST_CONFIRM: "Aceept Request",
  ACCEPT_REQUEST_CANCEL: "Cancel it",
  ACCEPT_SUCCESS: "Accepted Successfully",
  REJECT_REQUEST_TITLE: "Are you sure?",
  REJECT_REQUEST_TEXT: "Reject Request",
  REJECT_REQUEST_CONFIRM: "Reject Request",
  REJECT_REQUEST_CANCEL: "Cancel",
  REJECT_SUCCESS: "Ride Rejected Successfully",
  COMPLETE_RIDE_TITLE: "Are you sure?",
  COMPLETE_RIDE_TEXT: "You want the End-Ride",
  COMPLETE_RIDE_CONFIRM: "Yes, end ride!",
  COMPLETE_RIDE_SUCCESS: "Ride Completed Successfully Collect money from Customer",
  ERROR_UPDATING_STATUS: "Error updating ride status:"
};

export const CURRENTRIDETABLETRANSPORTER_ENDPOINTS = {
  UPDATE_RIDE_STATUS: "http://localhost:8091/api/bookings/update-ride-status/"
};

//HistoryTable
export const HISTORYTRANSPORTER_HEADERS = {
  HISTORY:"Completed and Rejected Rides",
  REQUEST_ID: "Request Id",
  SOURCE: "Source",
  DESTINATION: "Destination",
  STATUS: "Status",
  DETAILS: "Details"
};

export const HISTORYTRANSPORTER_BUTTON_TITLES = {
  DETAILS: "Details"
};

export const HISTORYTRANSPORTER_MESSAGES = {
  NO_DATA: "No completed or Rejected Data",
  ERROR_FETCHING_DATA: "Error fetching history data:"
};

export const HISTORYTRANSPORTER_ENDPOINTS = {
  FETCH_HISTORY: "http://localhost:8091/api/bookings/completed-or-rejected/transporter/"
};

//RideDetailsModal
export const RIDEDETAILSMODAL_TITLES = {
  MODAL_TITLE: "Ride Details",
  CUSTOMER_NAME: "Customer Name:",
  EMAIL: "Email:",
  PHONE_NUMBER: "Phone number:",
  TOTAL_PRICE: "Total Price:",
  RIDE_DATE: "Ride Date:",
  RIDE_TIME: "Ride Time:",
  CLOSE_BUTTON: "Close"
};


//TransporterDashBoard
export const TRANSPORTERDASHBOARD_TABS = {
  CURRENT_RIDE: 'currentRide',
  HISTORY: 'history',
  STATISTICS:'statistics'
};

export const TRANSPORTERDASHBOARD_BUTTON_TITLES = {
  CURRENT_RIDE: 'Current Ride',
  HISTORY: 'History',
  STATISTICS: 'Statistics',
  LOGOUT: 'LogOut'
};

export const TRANSPORTERDASHBOARD_AVAILABILITY = {
  AVAILABLE: 'Available',
  NOT_AVAILABLE: 'Not Available'
};

export const TRANSPORTERDASHBOARD_MESSAGES = {
  ERROR: 'Error!',
  SOMETHING_WENT_WRONG: 'Something went wrong',
  SUCCESS: 'Success!'
};

export const TRANSPORTERDASHBOARD_ENDPOINTS = {
  VEHICLE_INFO: 'http://localhost:8091/api/bookings/vehicle-info/',
  ACCEPTED_OR_PENDING: 'http://localhost:8091/api/bookings/accepted-or-pending/transporter/',
  UPDATE_VEHICLE_STATUS: 'http://localhost:8091/api/bookings/',
  UPDATE_PRICES:"http://localhost:8091/api/bookings",
  BASE_PRICE_PRICE_PER_KM:"http://localhost:8091/api/bookings"
};


//CurrentRideTableCustomer
export const CURRENTRIDETABLECUSTOMER_HEADERS = {
  SOURCE: "Source",
  DESTINATION: "Destination",
  RIDE_DATE: "Ride Date",
  RIDE_TIME: "Ride Time",
  STATUS: "Status",
  DETAILS: "Details",
  OTHERS: "Others"
};

export const CURRENTRIDETABLECUSTOMER_BUTTON_TITLES = {
  DETAILS: "Details",
  CANCEL: "Cancel"
};

export const CURRENTRIDETABLECUSTOMER_MESSAGES = {
  PENDING_REQUESTS_TITLE: "Pending Requests and Rides",
  NO_PENDING_REQUESTS: "No pending or accepted requests",
  REJECT_REQUEST_TITLE: "Are you sure?",
  REJECT_REQUEST_TEXT: "Do you want to reject this request?",
  REJECT_REQUEST_CONFIRM: "Reject Request",
  REJECT_REQUEST_CANCEL: "Cancel",
  REJECT_SUCCESS: "Ride Rejected Successfully",
  REJECT_ERROR: "Failed to reject the ride. Please try again later.",
  ERROR_UPDATING_STATUS: "Error updating ride status:"
};

export const CURRENTRIDETABLECUSTOMER_ENDPOINTS = {
  UPDATE_RIDE_STATUS: "http://localhost:8091/api/bookings/update-ride-status/"
};


//HistoryTablecustomer
export const HISTORYTABLECUSTOMER_HEADERS = {
  REQUEST_ID: "Request Id",
  SOURCE: "Source",
  DESTINATION: "Destination",
  STATUS: "Status",
  DETAILS: "Details"
};

export const HISTORYTABLECUSTOMER_BUTTON_TITLES = {
  DETAILS: "Details"
};

export const HISTORYTABLECUSTOMER_MESSAGES = {
  TITLE: "Completed and Rejected Rides",
  NO_DATA: "No completed or Rejected Data",
  ERROR_FETCHING_DATA: "Error fetching history data:"
};

export const HISTORYTABLECUSTOMER_ENDPOINTS = {
  FETCH_HISTORY: "http://localhost:8091/api/bookings/completed-or-rejected/customer/"
};


//PendingPayments
export const PENDINGPAYMENTS_HEADERS = {
  SOURCE: "Source",
  DESTINATION: "Destination",
  RIDE_DATE: "Ride Date",
  RIDE_TIME: "Ride Time",
  STATUS: "Status",
  DETAILS: "Details",
  OTHERS: "Others"
};

export const PENDINGPAYMENTS_BUTTON_TITLES = {
  DETAILS: "Details",
  PAY: "Pay"
};

export const PENDINGPAYMENTS_MESSAGES = {
  TITLE: "Pending Payments",
  NO_PENDING_PAYMENTS: "No pending Payments",
  ERROR: "Error!",
  SOMETHING_WENT_WRONG: "Something went wrong",
  PAYMENT_SUCCESS: "Payment successful",
  RECORD_PAYMENT_ERROR: "Failed to record payment",
  REJECT_REQUEST_TITLE: "Are you sure?",
  REJECT_REQUEST_TEXT: "Do you want to reject this request?",
  REJECT_REQUEST_CONFIRM: "Reject Request",
  REJECT_REQUEST_CANCEL: "Cancel",
  REJECT_SUCCESS: "Ride Rejected Successfully",
  REJECT_ERROR: "Failed to reject the ride. Please try again later.",
  ERROR_UPDATING_STATUS: "Error updating ride status:"
};

export const PENDINGPAYMENTS_ENDPOINTS = {
  FETCH_PAYMENTS: "http://localhost:8091/api/bookings/api/payment/customer/",
  UPDATE_RIDE_STATUS: "http://localhost:8091/api/bookings/update-ride-status/",
  SAVE_PAYMENT: "http://localhost:8091/payments/save"
};

export const PENDINGPAYMENTS_RAZORPAY_OPTIONS = {
  KEY: "rzp_test_vv1FCZvuDRF6lQ",
  CURRENCY: "INR",
  NAME: "SpeedyGo",
  DESCRIPTION: "Ride Payment",
  THEME_COLOR: "gold"
};

//RideCustomerModal
export const RIDECUSTOMERMODAL_TITLES = {
  MODAL_TITLE: "Ride Details",
  REQUEST_ID: "Request Id:",
  DISTANCE: "Distance:",
  TOTAL_PRICE: "Total Price:",
  TRANSPORTER_NAME: "Transporter Name:",
  TRANSPORTER_EMAIL: "Transporter Email:",
  PHONE_NUMBER: "PhoneNumber:",
  CLOSE_BUTTON: "Close"
};

//USERDASHBOARD
export const USERDASHBOARD_TABS = {
  CURRENT_RIDE: 'currentRide',
  PENDING_PAYMENTS: 'pendingPayments',
  HISTORY: 'history'
};

export const USERDASHBOARD_BUTTON_TITLES = {
  CURRENT_RIDE: 'Current Ride',
  PENDING_PAYMENTS: 'Pending Payments',
  HISTORY: 'History',
  LOGOUT: 'LogOut'
};

export const USERDASHBOARD_MESSAGES = {
  DASHBOARD_TITLE: 'Dashboard',
  ERROR_FETCHING_DATA: 'Error fetching customer data:'
};

export const USERDASHBOARD_ENDPOINTS = {
  FETCH_CUSTOMER_DETAILS: 'http://localhost:8091/api/bookings/accepted-or-pending/customer/'
};

//ForgotPassword

export const FORGOTPASSWORD_MESSAGES = {
  EMAIL_NOT_REGISTERED: "Email not registered",
  OTP_GENERATION_ERROR: "There was an error generating the OTP:",
  OTP_GENERATION_FAILED: "Failed to send OTP.",
  OTP_VALIDATION_ERROR: "There was an error validating the OTP:",
  OTP_VALIDATION_FAILED: "Failed to validate OTP.",
  PASSWORD_RESET_ERROR: "There was an error resetting the password:",
  PASSWORD_RESET_FAILED: "Failed to reset password."
};

export const FORGOTPASSWORD_ENDPOINTS = {
  EMAIL_PRESENT: "http://localhost:8091/emailPresent",
  OTP_GENERATE: "http://localhost:8091/otp/generate",
  OTP_VALIDATE: "http://localhost:8091/otp/validate",
  RESET_PASSWORD: "http://localhost:8091/reset-password"
};

export const FORGOTPASSWORD_VALIDATION_SCHEMA = {
  EMAIL: Yup.object({
    email: Yup.string().email('Invalid email address').required('Email is required'),
  }),
  PASSWORD: Yup.object({
    newPassword: Yup.string()
      .min(8, 'Password must be at least 8 characters')
      .matches(/[A-Z]/, 'Must contain one uppercase letter')
      .matches(/[a-z]/, 'Must contain one lowercase letter')
      .matches(/[0-9]/, 'Must contain one number')
      .matches(/[!@#$%^&*(),.?":{}|<>]/, 'Must contain one special character')
      .required('Password is required'),
    confirmPassword: Yup.string()
      .oneOf([Yup.ref('newPassword'), null], 'Passwords must match')
      .required('Confirm your password'),
  })
};

export const FORGOTPASSWORD_UI_TEXT = {
  FORGOT_PASSWORD_TITLE: "Forgot Your Password?",
  ENTER_EMAIL_PROMPT: "Enter your email to receive an OTP.",
  EMAIL_LABEL: "Email Address",
  EMAIL_PLACEHOLDER: "Enter your email",
  SEND_OTP_BUTTON: "Send OTP",
  ENTER_OTP_TITLE: "Enter OTP",
  OTP_SENT_PROMPT: "We have sent an OTP to your email.",
  VERIFY_OTP_BUTTON: "Verify OTP",
  RESET_PASSWORD_TITLE: "Reset Your Password",
  NEW_PASSWORD_LABEL: "New Password",
  NEW_PASSWORD_PLACEHOLDER: "Enter new password",
  CONFIRM_PASSWORD_LABEL: "Confirm Password",
  CONFIRM_PASSWORD_PLACEHOLDER: "Confirm new password",
  RESET_PASSWORD_BUTTON: "Reset Password"
};

//HOMEPAGE
export const HOMEPAGE_SERVICES = [
  {
    title: "Find Reliable Local Transporters",
    text: "Compare local transport services that fit your budget.",
    image: "feature1"
  },
  {
    title: "Get Instant Quotes for Moving",
    text: "Receive instant quotes from local movers.",
    image: "feature2"
  },
  {
    title: "Flexible Scheduling Options",
    text: "Choose transport services that fit your schedule.",
    image: "feature3"
  },
];

export const HOMEPAGE_CONTENT_ARRAY = [
  {
    title: "Find Reliable Local Transporters",
    text: "Quickly search and compare local transport services that fit your budget and schedule. Choose from trusted providers in your area.",
    image: "service1"
  },
  {
    title: "Get Instant Quotes for Moving",
    text: "Save time by getting instant quotes from multiple local movers. Compare prices and services to make an informed decision for your transport needs.",
    image: "service2"
  },
  {
    title: "Flexible Scheduling Options",
    text: "Choose a transport service that fits your timing. With flexible scheduling options, you can plan your move or delivery at your convenience.",
    image: "service3"
  },
];

export const HOMEPAGE_UI_TEXT = {
  WELCOME_TITLE: "Welcome to SpeedyGo",
  WELCOME_DESCRIPTION: "SpeedyGo is your go-to platform for finding cost-effective local transporters. Whether you're moving homes or need a quick transport solution, compare quotes from local providers and choose the best option for you.",
  DISCOVER_CONTENT: "Discover amazing content and explore now!",
  EXPLORE_NOW_BUTTON: "Explore Now",
  OUR_SERVICES_TITLE: "Our Services",
  FEATURES_TITLE: "Features of Speedygo",
  SWITCH_TO_LIGHT_MODE: "Switch to Light Mode",
  SWITCH_TO_DARK_MODE: "Switch to Dark Mode"
};
