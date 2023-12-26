import asyncio
import os

import websockets
from dotenv import load_dotenv


async def handler(websocket):
    print("Client Connected")

    while True:
        message = await websocket.recv()
        print(message)
        await websocket.send(f"message from server: {message}")


async def main():
    load_dotenv()
    IP_ADDRESS = os.getenv('IP_ADDRESS')
    PORT = 3000

    async with websockets.serve(handler, IP_ADDRESS, PORT):
        print("SERVER STARTED: RUNNING AT PORT 3000")
        await asyncio.Future()  # run forever


if __name__ == "__main__":
    asyncio.run(main())
