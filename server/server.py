import asyncio
import websockets

async def handle_client(websocket):
    print('function')

    try:
        print(f"Client connected from {websocket.remote_address}")

        while True:
            message = await websocket.recv()
            print(f"Received message: {message}")

            response = f"Server received: {message}"
            await websocket.send(response)

    except websockets.exceptions.ConnectionClosedOK:
        print(f"Client {websocket.remote_address} disconnected.")


start_server = websockets.serve(handle_client, 'localhost', 3000)

asyncio.get_event_loop().run_until_complete(start_server)
asyncio.get_event_loop().run_forever()
