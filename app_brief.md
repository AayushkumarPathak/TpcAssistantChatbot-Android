# TPC Assistant Chatbot Application Brief

This document provides a brief overview of the application's workflow and the key components used in its implementation.

## 1. Authentication Flow (Login)

- **LoginActivity**: This is the entry point of the application for unauthenticated users.
    - **UI**: It consists of two `EditText` fields (Registration Number and Password) and a `Login` button.
    - **Network**: When the user clicks login, the app sends a `LoginRequest` via **Retrofit** to the backend.
    - **Token Management**: Upon a successful response, the app receives a JWT (JSON Web Token). This token is securely saved in `SharedPreferences` (managed by `TokenManager`) to keep the user logged in for future sessions.
    - **Navigation**: Once authenticated, the user is navigated to the `ChatActivity`.

## 2. Chat Workflow

- **ChatActivity**: This is the main interface where the user interacts with the chatbot.
    - **UI Initialization**: It initializes the `RecyclerView`, `EditText` for message input, and a `Send` button.
    - **Sending Messages**: 
        - When the user types a message and clicks 'Send', a `ChatHistoryItem` is created with the role "student".
        - The message is immediately added to the local `chatHistory` list and displayed in the `RecyclerView`.
        - A network request is then made using `ChatService` to get a response from the chatbot.
    - **Receiving Responses**: 
        - The bot's response is received and added to the `chatHistory`.
        - The UI is updated to show the bot's message.
        - If the bot determines that a human intervention is needed (e.g., complex queries), a "Human Required" indicator is displayed.

## 3. Key UI Components

### RecyclerView
Used to display the list of chat messages efficiently. It only renders the items currently visible on the screen, which is crucial for performance as the chat history grows.

### Adapters (`ChatAdapter`)
The `ChatAdapter` acts as a bridge between the data (list of `ChatHistoryItem`) and the `RecyclerView`.
- **Multiple View Types**: It handles two different layouts:
    - `item_message_user.xml`: Used for messages sent by the student (aligned to the right).
    - `item_message_bot.xml`: Used for messages sent by the chatbot (aligned to the left).
- **ViewHolders**: It uses `UserViewHolder` and `BotViewHolder` to cache references to the views within each list item, improving scrolling performance.

## 4. Architecture and Tools Used

- **Retrofit**: Used for making type-safe HTTP requests to the backend API.
- **Coroutines (`lifecycleScope`)**: Used to handle asynchronous network calls on a background thread without blocking the UI.
- **SharedPreferences**: Used for persistent storage of the authentication token.
- **Material Design Components**: Used for a modern and responsive UI/UX.
