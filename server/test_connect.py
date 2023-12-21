import asyncio
import websockets

async def connect_to_server():
    uri = "ws://IP ADDRESS:3000"  # Replace with your WebSocket server's URI

    async with websockets.connect(uri) as websocket:
        print(f"Connected to {uri}")

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
