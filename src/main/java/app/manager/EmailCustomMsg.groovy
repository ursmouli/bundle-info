package app.manager

enum EmailCustomMsg {

    FAILED_SEND_MAIL {
        @Override
        String toString() {
            return "Error in sending email"
        }
    },
    NULL_EMAIL_LIST {
        @Override
        String toString() {
            return "Email list is NULL"
        }
    },
    EMPTY_EMAIL_LIST {
        @Override
        String toString() {
            return "Empty email list"
        }
    }
}