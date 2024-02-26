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
        return `${hoursPassed} hour(s)`;
    } else if (minutesPassed > 0) {
        return `${minutesPassed} minutes`;
    } else {
        return 'right now';
    }
}

export function getTimeUntil(specificDate) {
    const futureDateTime = new Date(specificDate);
    const now = new Date();
    const differenceInTime = futureDateTime - now;

    if (differenceInTime < 0) {
        return 'The date has already passed';
    }

    const minutesUntil = Math.floor(differenceInTime / (1000 * 60));
    const hoursUntil = Math.floor(minutesUntil / 60);
    const daysUntil = Math.floor(hoursUntil / 24);
    const monthsUntil = Math.floor(daysUntil / 30);
    const yearsUntil = Math.floor(daysUntil / 365);

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

export function getDaysOfMonth(year, month) {
    return new Date(year, month + 1, 0).getDate();
}

export function generateCalendar(year) {
    const ROWS = 7;
    const MONTHS = 12;

    let calendar = new Array(ROWS).fill(null).map(() => []);
    let lastExtraDay = null;
    for (let month = 0; month < MONTHS; month++) {
        const daysOfMonth = getDaysOfMonth(year, month);
        const columnsNumber = Math.floor(daysOfMonth / ROWS);
        const extraDays = daysOfMonth % ROWS;

        for (let i = 0; i < ROWS; i++) {
            let row = i;

            if (lastExtraDay) {
                row += lastExtraDay;
                row %= ROWS;
            }
            let startDay = i + 1;

            for (let column = 0; column < columnsNumber; column++) {
                let day = startDay + (column * ROWS);
                calendar[row].push({day, month: month});
            }
            if (extraDays > i) {
                let day = startDay + (columnsNumber * ROWS) + 1;
                calendar[row].push({day: day - 1, month: month});
            }
        }
        lastExtraDay = (lastExtraDay + extraDays) % ROWS;
    }
    return calendar;
}

export const months = {
    0: 'January',
    1: 'February',
    2: 'March',
    3: 'April',
    4: 'May',
    5: 'June',
    6: 'July',
    7: 'August',
    8: 'September',
    9: 'October',
    10: 'November',
    11: 'December'
}

export const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];