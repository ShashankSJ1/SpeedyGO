// DashedNumber.js
import React from 'react';
import { useSpring, animated } from 'react-spring';

const DashedNumber = ({ value }) => {
  const {number}=useSpring({
    from: {number: 0},
    number: value,
    delay: 300,
    config:{mass:1,tension:20,friction:10},
  });

  return (
    <animated.span>
        {number.to(n => n.toFixed(0))}
    </animated.span>
  );
};

export default DashedNumber;
