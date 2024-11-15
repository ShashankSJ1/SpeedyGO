// userSlice.test.js
import userReducer, { setUser, clearUser } from '../../Redux/Reducer'; // Adjust the import path as necessary

describe('userSlice', () => {
  const initialState = {
    userId: null,
    name: null,
    email: null,
    role: null,
    phoneNumber: null,
    isLoggedIn: false,
  };

  test('should return the initial state', () => {
    expect(userReducer(undefined, {})).toEqual(initialState);
  });

  test('should set user information when setUser is called', () => {
    const user = {
      userId: '123',
      name: 'John Doe',
      email: 'john.doe@example.com',
      role: 'admin',
      phoneNumber: '123-456-7890',
    };

    const newState = userReducer(initialState, setUser(user));
    
    expect(newState).toEqual({
      ...initialState,
      userId: user.userId,
      name: user.name,
      email: user.email,
      role: user.role,
      phoneNumber: user.phoneNumber,
      isLoggedIn: true,
    });
  });

  test('should clear user information when clearUser is called', () => {
    const loggedInState = {
      userId: '123',
      name: 'John Doe',
      email: 'john.doe@example.com',
      role: 'admin',
      phoneNumber: '123-456-7890',
      isLoggedIn: true,
    };

    const newState = userReducer(loggedInState, clearUser());

    expect(newState).toEqual(initialState);
  });
});
