# Eh Iniscroll

## Overview

Iniscroll is an innovative application that bridges the gap between your smartphone and Windows PC, providing a seamless remote scrolling experience. With Iniscroll, you can effortlessly control scrolling on your phone and observe it mirrored on your PC screen.

## Getting Started

### Prerequisites

- Android device with the Eh Iniscroll app installed

- [Python](https://www.python.org/downloads/) installed on your Windows PC.

- Ensure you have [pip](https://pip.pypa.io/en/stable/installation/) installed.

- The websocket and pynput modules for Python should be installed

### Installing Required Python Modules

Open a command prompt and run the following commands to install the necessary Python modules:

```bash
pip install websocket-client
```
    pip install pynput

### Installation

1. Download the APK from the repository.
2. Install the APK on your Android device.

### Running the Python Server
1. Download the server.py file from the repository inside the server folder

2. Open a command prompt and navigate to the directory where 'server.py' is located.

3. Run the following command to start the server:
    ```bash
    python server.py 
    ```
    If you are using Python 3, you might need to use python3 instead of python:
    ```bash
    python3 server.py 
    ```
4. The server will print the IP address at the console. Take note of this IP address.

### Connecting the Eh Iniscroll App
1. Launch the Eh Iniscroll app on your Android device.

2. Input the IP address displayed by the server into the Eh Iniscroll app. The IP address should be located at the topmost of the screen.

3. The phone should now be connected with the computer, and you can start scrolling seamlessly.

## Technologies Used

- Android SDK
- Java 
- WebSocket 

## Contributing

If you want to contribute to this project, follow these steps:

1. Fork the project.
2. Create a new branch (`git checkout -b feature/your-feature-name`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/your-feature-name`).
5. Open a pull request.

## License

[MIT](https://choosealicense.com/licenses/mit/)
