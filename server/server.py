import asyncio
import websockets


async def handler(websocket):
    while True:
        message = await websocket.recv()
        print(message)
        await websocket.send(f"message from server: {message}")


async def main():
    async with websockets.serve(handler, "localhost", 8001):
        await asyncio.Future()  # run forever


if __name__ == "__main__":
    asyncio.run(main())
