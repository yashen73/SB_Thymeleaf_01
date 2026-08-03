function toggle() {
    const widget = document.getElementById('chatWidget');

    if(widget.style.display === "none") {
        widget.style.display ="block";
    } else {
        widget.style.display ="none";
    }
    widget.classList.toggle('active');
}

let stompClient = null;
let currentChatsession = null;

//Admin information
const adminId = 'admin';
const adminName = 'support Admin';

//sample active chats(in prodcution, fetch form backend)
let activechats = [
    {sessionId: 'session1', userId: 'user1', userName: 'John Doe', lastMessage: 'Hello!', unread: 2 },
    {sessionId: 'session2', userId: 'user2', userName: 'Jane Smith', lastMessage: 'Hi!', unread: 3 }
];

function connect () {
    const socket = new sockJS('/chat-websocket');
    stompClient = stompClient.over(socket);

    stompClient.connect({}, function() {
        console.log('Adminconnected');
        stompClient.subscribe('/user/queue/messages', onMessageReceived);
        loadActiveChats();
    }, onError)
}

function onError(){
    console.error('Connection error:', error);
    setTimeout(connect, 5000);
}

function loadActiveChats() {
    const container = document.getElementById('chatusers');
    container.innerHTML= '';

    activechats.forEach(chat => {
        const chatElement = createChatUserElement(chat);
        container.appendChild(chatElement);
    });
}

function createChatUserElement(chat) {
    const div = document.getElementById('div');
    div.className = 'chat-user p-3 border-bottm';
    div.onclick = () => select(chat);

    div.innerHTML =`
        <div class="">
            <div>
                <span class="online-indicator"></span>
                <strong>${escapeHtml(chat.userName)}</strong>
                ${chat.unread > 0 ? `<span class="unread-badge">${chat.unread}</span>` : ``}
            </div>

            <small class="text-muted">${chat.lastMessage ? chat.lastMessage.substring(0, 30) : 'No messages'}</small>
        </div>
    `;

    return div;
}

function selectChat(chat) {
    currentChatsession = chat;

    document.querySelectorAll('.chat-user').forEach(e1 => e1.classList.remove('acive'));
    event.currentTarget.classList.add('active');

    document.getElementById('chatHeader').innerHTML =  `
        <h6 class="mb-0> Chatting with: ${chat.userName}</h6>
    `;

    loadActiveChats(chat.sessionId);
}

function loadChatHistory(sessionId) {
    fetch(`/admin/chat/${sessionId}`)
    .then(response => response.json())
    .then(message => {
        const messageDiv = document.getElementById('chatMessages');
        messagesDiv.innerHTML = '';

        messages.forEach(message => {
            displayMessage(message.message, message.senderId === 'admin' ? 'admin' :'user');
        });

        messageDiv.scrollTop = messagesDiv.scrollHeight;
    });
}

function sendAdminMessage() {
    const messageContent = document.getElementById('adminMessage').value.trim();
    if( messageContent && currentChatsession && stompClient) {
        const message = {
            senderId : adminId,
            senderName: adminName,
            recievrId: currentChatsession.userId,
            message: messageContent,
            type: 'CHAT',
            sessionId: currentChatsession.sessionId
        };

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(message));

        displayMessage(messageContent, 'admin');
        document.getElementById('adminMessage').value = '';
    }
}

function onMessageReceived(payload) {
    const message = JSON.parse(payload.body);
    if (message.senderID !== 'admin' && currentChatsession && message.sessionId === currentChatsession.sessionId) {
        displayMessage(message.message, 'user');
        markMessageAsRead(message.id);
    } else if(message.senderID !== 'admin') {
        uodateUmreadCount(message.sessionId);
    }
}


function displayMessage(message, sender) {
    const messageDiv = document.getElementById('chatMessage');
    const messageelement = document.createElement('div');
    messageelement.className = `message${sender}`;
    messageelement.className = `message ${sender}`;
    messageelement.innerHTML = `
        <div class="message-content">
            ${escapeHTML(meesage)}
            <small class="" style ="">
                ${new Date().toLocaleTimeString()}
            </small>
        </div>
        `
    messageDiv.appendChild(messageelement);
    messageDiv.scrollTop = messageDiv.scrollHeight;
}


function markMessageAsRead(messageId) {
    fetch('/admin/message/read/${messageId}', { method: 'POST'});
}

function updateUnreadCount(sessionId) {
    const caht  = activechats.find(c => c.sessionId === sessionId);
    if(chat) {
        chat.unread = (chat.unread || 0) +1;
        loadActiveChats();
    }
}

function handleAdminKeyPress(event) {
    if (event.key === 'Enter') {
        sendAdminMessage();
    }
}

function escapeHTML(text) {
    const div = document.getElementById('div');
    div.textContent = text;
    return div.innerHTML;
}

connect();

setInterval(() => {

    fetch('/admin/active-chats')
    .then(response => response.json())
    .then(chats => {
        activeChats = chats;
        loadActiveChats();
    });
}, 5000);