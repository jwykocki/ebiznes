const express = require("express");
const session = require("express-session");
const cors = require("cors");
const passport = require("passport");
const jwt = require("jsonwebtoken");

require("dotenv").config();

require("./auth/google");

const { findOrCreateUser } = require("./db/users");

const app = express();
const PORT = 5000;

app.use(cors({ origin: process.env.FRONTEND_URL, credentials: true }));
app.use(express.json());
app.use(
    session({
        secret: "secret_session_key",
        resave: false,
        saveUninitialized: true,
    })
);
app.use(passport.initialize());
app.use(passport.session());


app.get("/auth/google", passport.authenticate("google", { scope: ["profile", "email"] }));

app.get(
    "/auth/google/callback",
    passport.authenticate("google", { failureRedirect: "/" }),
    async (req, res) => {
        const user = await findOrCreateUser(req.user);
        const token = jwt.sign({ id: user.id }, process.env.JWT_SECRET, { expiresIn: "1h" });

        res.redirect(`${process.env.FRONTEND_URL}/oauth-callback?token=${token}`);
    }
);

app.get("/me", (req, res) => {
    const authHeader = req.headers.authorization;
    const token = authHeader && authHeader.split(" ")[1];
    if (!token) return res.sendStatus(401);

    jwt.verify(token, process.env.JWT_SECRET, (err, user) => {
        if (err) return res.sendStatus(403);
        res.json({ userId: user.id });
    });
});

app.listen(PORT, () => console.log(`Server running on http://localhost:${PORT}`));
