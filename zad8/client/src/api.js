export async function getUser(token) {
    const res = await fetch("http://localhost:5000/me", {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    });

    if (!res.ok) throw new Error("Unauthorized");
    return await res.json();
}
