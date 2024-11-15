import { setUser } from '../../Redux/Reducer';
import { store,persistor } from '../../Redux/Store';

describe('Redux Store', () => {
  afterEach(() => {
    // Reset the store after each test to prevent test interference
    persistor.purge(); // Clear persisted store
  });

  test('should have the initial state', () => {
    const state = store.getState();
    expect(state).toEqual({
      user: {
        userId: null,
        name: null,
        email: null,
        role: null,
        phoneNumber: null,
        isLoggedIn: false,
      },
    });
  });

  test('should set user information in the store', () => {
    const user = {
      userId: '123',
      name: 'John Doe',
      email: 'john.doe@example.com',
      role: 'admin',
      phoneNumber: '123-456-7890',
    };

    store.dispatch(setUser(user)); // Dispatch the action to set user

    const state = store.getState();
    expect(state.user).toEqual({
      userId: user.userId,
      name: user.name,
      email: user.email,
      role: user.role,
      phoneNumber: user.phoneNumber,
      isLoggedIn: true,
    });
  });

  test('should clear user information in the store', () => {
    const user = {
      userId: '123',
      name: 'John Doe',
      email: 'john.doe@example.com',
      role: 'admin',
      phoneNumber: '123-456-7890',
    };

    // First set the user
    store.dispatch(setUser(user));

    // Now clear the user
    store.dispatch(clearUser());

    const state = store.getState();
    expect(state.user).toEqual({
      userId: null,
      name: null,
      email: null,
      role: null,
      phoneNumber: null,
      isLoggedIn: false,
    });
  });

  test('should persist user data', () => {
    const user = {
      userId: '123',
      name: 'John Doe',
      email: 'john.doe@example.com',
      role: 'admin',
      phoneNumber: '123-456-7890',
    };

    store.dispatch(setUser(user));

    // Simulate a refresh or app restart by creating a new store
    const newStore = configureStore({
      reducer: persistedReducer,
      middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware({
          serializableCheck: false,
        }),
    });

    const state = newStore.getState();
    expect(state.user).toEqual({
      userId: user.userId,
      name: user.name,
      email: user.email,
      role: user.role,
      phoneNumber: user.phoneNumber,
      isLoggedIn: true,
    });
  });
});
