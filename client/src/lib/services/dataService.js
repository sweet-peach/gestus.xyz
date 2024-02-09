export function getTimePassed(specificDate) {
    const specificDateTime = new Date(specificDate);
    const now = new Date();
    const differenceInTime = now - specificDateTime;

    const minutesPassed = Math.floor(differenceInTime / (1000 * 60));
    const hoursPassed = Math.floor(minutesPassed / 60);
    const daysPassed = Math.floor(hoursPassed / 24);
    const monthsPassed = Math.floor(daysPassed / 30);
    const yearsPassed = Math.floor(daysPassed / 365);

    if (yearsPassed > 0) {
        return 'more than year';
    } else if (monthsPassed > 0) {
        return 'more than one month';
    } else if (daysPassed > 0) {
        return `${daysPassed} days`;
    } else if (hoursPassed > 0) {
        return `${hoursPassed} hours`;
    } else if (minutesPassed > 0) {
        return `${minutesPassed} minutes`;
    } else {
        return 'right now';
    }
}

export function getTimeUntil(specificDate) {
    const futureDateTime = new Date(specificDate);
    const now = new Date();
    const differenceInTime = futureDateTime - now; // разница в миллисекундах

    if (differenceInTime < 0) {
        return 'The date has already passed';
    }

    const minutesUntil = Math.floor(differenceInTime / (1000 * 60));
    const hoursUntil = Math.floor(minutesUntil / 60);
    const daysUntil = Math.floor(hoursUntil / 24);
    const monthsUntil = Math.floor(daysUntil / 30); // приблизительное значение
    const yearsUntil = Math.floor(daysUntil / 365); // приблизительное значение

    if (yearsUntil > 0) {
        return `more than (${yearsUntil} years)`;
    } else if (monthsUntil > 0) {
        return ` (more than ${monthsUntil} months)`;
    } else if (daysUntil > 0) {
        return `${daysUntil} days`;
    } else if (hoursUntil > 0) {
        return `${hoursUntil} hours`;
    } else if (minutesUntil > 0) {
        return `${minutesUntil} minutes`;
    } else {
        return 'right now';
    }
}

// Пример использования:
console.log(getTimeUntil("2024-12-25T00:00:00.000+00:00"));
