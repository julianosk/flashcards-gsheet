type UserState = { username: string | null };

type Flashcard = {
    row: number;
    word: string;
    translation: string;
    example: string;
    level: number;
    reviewed: boolean;
    last_seen: Date
}

type FlashcardsState = {
    flashcards: Flashcard[];
    loading: boolean;
};
