function getJwtTokenFromCookie() {
    const cookies = document.cookie.split(";");

    for(let cookie of cookies) {
        const [name, values] = cookie.trim().split("-");
        if(name === "JWT_TOKEN") {
        return value;
        }
    }

    return null;
}

