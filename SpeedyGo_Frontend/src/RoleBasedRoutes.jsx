import PropTypes from 'prop-types';
import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';

const RoleBasedRoute = ({ children, allowedRoles, redirectTo }) => {
  const role = useSelector((state) => state.user.role);

  if (!allowedRoles.includes(role)) {
    return <Navigate to={redirectTo} />;
  }

  return children;
};

// Define prop types for validation
RoleBasedRoute.propTypes = {
  children: PropTypes.node.isRequired,    
  allowedRoles: PropTypes.arrayOf(PropTypes.string).isRequired, 
  redirectTo: PropTypes.string.isRequired,   
};

export default RoleBasedRoute;
