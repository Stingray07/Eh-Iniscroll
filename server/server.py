import asyncio
import os
import websockets
from dotenv import load_dotenv
from websockets.exceptions import ConnectionClosed
from pynput.mouse import Controller


async def handler(websocket):
    print("Client Connection Open")
    mouse_controller = Controller()
    try:
        while True:
            message = await websocket.recv()

            # Adjust sensitivity here
            sensitivity = 0.075

            # Check if scroll down or up
            if message == "1":
                scroll_amount = -1 * sensitivity
            else:
                scroll_amount = 1 * sensitivity

            mouse_controller.scroll(0, scroll_amount)
    except ConnectionClosed:
        print("Client Connection Closed")


async def main():
    load_dotenv()
    IP_ADDRESS = os.getenv('IP_ADDRESS')
    PORT = 3000

    # Open WebSocket Server at IP and Port
    async with websockets.serve(handler, IP_ADDRESS, PORT):
        print(f"SERVER STARTED: RUNNING AT PORT {PORT}")
        await asyncio.Future()  # run forever


if __name__ == "__main__":
    asyncio.run(main())
