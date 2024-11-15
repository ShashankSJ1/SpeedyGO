import React from 'react';
import './SplashScreen.css'; // Importing CSS for styling
import splashvideo from "../src/images/Splash_SpeedyGo.mp4" 
function SplashScreen() {
  return (
    <div className="splash-screen">
      <div className="video-container">
        {/* Replace 'your-video-path.mp4' with the actual path of your video */}
        <video className="splash-video" autoPlay muted loop>
          <source src={splashvideo} type="video/mp4" />
          Your browser does not support the video tag.
        </video>
      </div>
    </div>
  );
}

export default SplashScreen;
