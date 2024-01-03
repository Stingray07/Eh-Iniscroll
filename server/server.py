import asyncio
import os
import websockets
from dotenv import load_dotenv
from websockets.exceptions import ConnectionClosed


async def handler(websocket):
    print("Client Connection Open")

    try:
        while True:
            message = await websocket.recv()
            print(message)
    except ConnectionClosed:
        print("Client Connection Closed")


async def main():
    load_dotenv()
    IP_ADDRESS = os.getenv('IP_ADDRESS')
    PORT = 3000

    async with websockets.serve(handler, IP_ADDRESS, PORT):
        print(f"SERVER STARTED: RUNNING AT PORT {PORT}")
        await asyncio.Future()  # run forever


if __name__ == "__main__":
    asyncio.run(main())
