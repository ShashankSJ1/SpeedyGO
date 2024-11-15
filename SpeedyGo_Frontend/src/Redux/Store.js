import { configureStore } from '@reduxjs/toolkit';
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage'; // Default to localStorage for web
import { combineReducers } from 'redux';
import userReducer from './Reducer'; // Adjust the path as necessary

// Create a persist configuration
const persistConfig = {
  key: 'root', // Key for localStorage
  storage, // Use localStorage
};

// Combine reducers
const rootReducer = combineReducers({
  user: userReducer, // User reducer
});

// Create a persisted reducer
const persistedReducer = persistReducer(persistConfig, rootReducer);

// Configure store with the persisted reducer and disable serializable check
const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false, // Disable serializable check middleware
    }),
});

// Create a persistor
const persistor = persistStore(store);

export { store, persistor };
