import { typedAction } from "../helpers";

// Actions
const LOGIN = "user/LOGIN";
const LOGOUT = "user/LOGOUT";

const initialState: UserState = { username: null };

export const login = (username: string) => {
    return typedAction(LOGIN, username);
};

export const logout = () => {
    return typedAction(LOGOUT);
};

type UserAction = ReturnType<typeof login | typeof logout>;

export function userReducer(
    state = initialState,
    action: UserAction
): UserState {
    switch (action.type) {
        case LOGIN:
            return { username: action.payload };
        case LOGOUT:
            return { username: null };
        default:
            return state;
    }
}
