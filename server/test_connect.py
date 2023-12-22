import asyncio
import websockets
import os
from dotenv import load_dotenv


async def connect_to_server():
    load_dotenv()
    IP_ADDRESS = os.getenv('IP_ADDRESS')
    PORT = 3000
    uri = f"ws://{IP_ADDRESS}:{PORT}"  # Replace with your WebSocket server's URI

    async with websockets.connect(uri) as websocket:
        print(f"Connected to WebSocket")

        message_to_send = input("Message to send: ")
        while message_to_send != 'q':

            await websocket.send(message_to_send)
            print(f"Sent message: {message_to_send}")

            # Receive and print the response from the server
            response = await websocket.recv()
            print(f"Received response: {response}")

            message_to_send = input("Message to send: ")

if __name__ == '__main__':
    asyncio.run(connect_to_server())
