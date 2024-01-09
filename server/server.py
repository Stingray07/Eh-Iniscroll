import asyncio
import websockets
from websockets.exceptions import ConnectionClosed
from pynput.mouse import Controller

# Adjust settings here
IP_ADDRESS = "192.168.100.4"
PORT = 3000
SENSITIVITY = 0.075


async def handler(websocket):
    print("Client Connection Open")
    mouse_controller = Controller()
    try:
        while True:
            message = await websocket.recv()

            # Check if scroll down or up
            if message == "1":
                scroll_amount = -1 * SENSITIVITY
            else:
                scroll_amount = 1 * SENSITIVITY

            mouse_controller.scroll(0, scroll_amount)
    except ConnectionClosed:
        print("Client Connection Closed")


async def main():
    # Open WebSocket Server at IP and Port
    async with websockets.serve(handler, IP_ADDRESS, PORT):
        print(f"SERVER STARTED: RUNNING AT {IP_ADDRESS}:{PORT}")
        await asyncio.Future()  # run forever


if __name__ == "__main__":
    asyncio.run(main())
