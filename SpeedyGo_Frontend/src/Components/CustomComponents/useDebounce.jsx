import { useRef, useEffect } from 'react';

function useDebounce(func, wait) {
    const timeoutRef = useRef(null);

    useEffect(() => {
        return () => {
            if (timeoutRef.current) {
                clearTimeout(timeoutRef.current);
            }
        };
    }, []);

    return function (...args) {
        if (timeoutRef.current) {
            clearTimeout(timeoutRef.current);
        }
        timeoutRef.current = setTimeout(() => {
            func.apply(this, args);
        }, wait);
    };
}

export default useDebounce;
