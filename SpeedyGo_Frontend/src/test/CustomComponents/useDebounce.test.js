// src/hooks/useDebounce.test.js
import { render, act } from '@testing-library/react';
import { useEffect, useState } from 'react';
import useDebounce from '../../Components/CustomComponents/useDebounce';

// A test component that uses the useDebounce hook
function TestComponent({ callback, wait }) {
    const debouncedCallback = useDebounce(callback, wait);
    const [value, setValue] = useState('');

    const handleChange = (e) => {
        setValue(e.target.value);
        debouncedCallback(e.target.value);
    };

    return <input type="text" value={value} onChange={handleChange} />;
}

describe('useDebounce', () => {
    jest.useFakeTimers();

    afterEach(() => {
        jest.clearAllTimers(); // Clear timers after each test
    });

    test('calls the callback after the specified wait time', () => {
        const mockCallback = jest.fn();
        render(<TestComponent callback={mockCallback} wait={300} />);

        act(() => {
            // Simulate typing in the input
            const input = document.querySelector('input');
            input.value = 'Hello';
            input.dispatchEvent(new Event('input'));
        });

        // Fast-forward time
        act(() => {
            jest.advanceTimersByTime(300);
        });

        expect(mockCallback).toHaveBeenCalledWith('Hello');
        expect(mockCallback).toHaveBeenCalledTimes(1); // Ensure it's called once
    });

    test('does not call the callback if invoked again before the wait time', () => {
        const mockCallback = jest.fn();
        render(<TestComponent callback={mockCallback} wait={300} />);

        act(() => {
            const input = document.querySelector('input');
            input.value = 'Hello';
            input.dispatchEvent(new Event('input'));
        });

        act(() => {
            const input = document.querySelector('input');
            input.value = 'Hello again';
            input.dispatchEvent(new Event('input'));
        });

        // Fast-forward time
        act(() => {
            jest.advanceTimersByTime(300);
        });

        expect(mockCallback).toHaveBeenCalledWith('Hello again');
        expect(mockCallback).toHaveBeenCalledTimes(1); // It should only be called once
    });

    test('cleans up the timeout on unmount', () => {
        const mockCallback = jest.fn();
        const { unmount } = render(<TestComponent callback={mockCallback} wait={300} />);

        act(() => {
            const input = document.querySelector('input');
            input.value = 'Hello';
            input.dispatchEvent(new Event('input'));
        });

        unmount(); // Unmount the component

        act(() => {
            jest.advanceTimersByTime(300);
        });

        expect(mockCallback).not.toHaveBeenCalled(); // Should not have been called
    });
});
