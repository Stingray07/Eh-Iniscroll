import asyncio
import websockets


async def handler(websocket):
    print("Client Connected")

    while True:
        message = await websocket.recv()
        print(message)
        await websocket.send(f"message from server: {message}")


async def main():
    async with websockets.serve(handler, 'IP ADDRESS', 3000):
        await asyncio.Future()  # run forever


if __name__ == "__main__":
    asyncio.run(main())
