// Convert UTC datetime to user device local datetime, and format it
export function formatDateTime(dateTimeUTC){
    const localDateTime = new Date(dateTimeUTC + 'Z');
    const options = {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric'
    };
    return new Intl.DateTimeFormat(navigator.language, options).format(localDateTime);
}

// Convert UTC datetime to user device local datetime, and format it
export function formatTime(dateTimeUTC){
    const localDateTime = new Date(dateTimeUTC + 'Z');
    const options = {
        hour: 'numeric',
        minute: 'numeric'
    };
    return new Intl.DateTimeFormat(navigator.language, options).format(localDateTime.getTime());
}