// LoginRegistration.test.js
import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import LoginRegistration from "../../pages/LoginRegistration"; // Adjust the import path as needed
// import Register from "../../Components/Login/Register";// Adjust the import path as needed
// import Login from "../../Components/Login/Login"; // Adjust the import path as needed

jest.mock("../../Components/Login/Register", () => (props) => (
  <div data-testid="register-form">
    <button onClick={props.toggleLogin}>Switch to Login</button>
  </div>
));

jest.mock("../../Components/Login/Login", () => (props) => (
  <div data-testid="login-form">
    <button onClick={props.toggleSignUp}>Switch to Signup</button>
  </div>
));

describe("LoginRegistration Component", () => {
  beforeEach(() => {
    render(<LoginRegistration />);
  });

  it("should render without crashing", () => {
    // Check if the component is rendered
    expect(screen.getByTestId("register-form")).toBeInTheDocument();
    expect(screen.getByTestId("login-form")).toBeInTheDocument();
  });

  it("should initially show the login form", () => {
    // Check for initial state
    expect(screen.getByTestId("login-form")).toBeVisible();
    expect(screen.queryByTestId("register-form")).not.toBeVisible();
  });

  it("should toggle to the registration form when signup button is clicked", () => {
    // Click the button to switch to signup
    fireEvent.click(screen.getByRole("button", { name: /Switch to Signup/i }));

    // Assert that the registration form is visible
    expect(screen.getByTestId("register-form")).toBeVisible();
    expect(screen.queryByTestId("login-form")).not.toBeVisible();
  });

  it("should toggle back to the login form when login button is clicked", () => {
    // First, switch to the signup form
    fireEvent.click(screen.getByRole("button", { name: /Switch to Signup/i }));

    // Then, switch back to the login form
    fireEvent.click(screen.getByRole("button", { name: /Switch to Login/i }));

    // Assert that the login form is visible again
    expect(screen.getByTestId("login-form")).toBeVisible();
    expect(screen.queryByTestId("register-form")).not.toBeVisible();
  });

  it("should have correct CSS classes based on signup state", () => {
    // Check the initial CSS classes
    expect(screen.getById("page")).toHaveClass("signin-show");
    
    // Switch to signup and check classes again
    fireEvent.click(screen.getByRole("button", { name: /Switch to Signup/i }));
    expect(screen.getById("page")).toHaveClass("signup-show");
  });
});
