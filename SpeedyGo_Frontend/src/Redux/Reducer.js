import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  userId: null, 
  name: null,
  email: null,
  role: null,
  phoneNumber: null, 
  isLoggedIn: false,
};


const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    // Action to set user information after login
    setUser: (state, action) => {
      const { userId, name, email, role, phoneNumber } = action.payload; // Include phoneNumber in the payload
      state.userId = userId;
      state.name = name;
      state.email = email;
      state.role = role;
      state.phoneNumber = phoneNumber; // Set phoneNumber
      state.isLoggedIn = true;
    },
    // Action to clear user information when logging out
    clearUser: (state) => {
      state.userId = null; // Clear userId
      state.name = null;
      state.email = null;
      state.role = null;
      state.phoneNumber = null; // Clear phoneNumber
      state.isLoggedIn = false;
    },
  },
});


// Export actions
export const { setUser, clearUser } = userSlice.actions;

// Export reducer
export default userSlice.reducer;