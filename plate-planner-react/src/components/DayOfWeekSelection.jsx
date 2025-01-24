function DayOfWeekSelection({ selectedDay, onDayChange }) {

    const daysOfTheWeek = [
        { value: 'SUNDAY', display: 'Sunday' },
        { value: 'MONDAY', display: 'Monday' },
        { value: 'TUESDAY', display: 'Tuesday' },
        { value: 'WEDNESDAY', display: 'Wednesday' },
        { value: 'THURSDAY', display: 'Thursday' },
        { value: 'FRIDAY', display: 'Friday' },
        { value: 'SATURDAY', display: 'Saturday' },
    ];

    const handleChange = (event) => {
        if (onDayChange) {
            onDayChange(event.target.value);
        }
    };

    return (
        <div>
            <label htmlFor="daySelect"></label>
            <select name="daySelect" id="daySelect" className="form-select" value={selectedDay} onChange={handleChange}>
                <option value="" disabled>Select a Day</option>
                {daysOfTheWeek.map(day => (
                    <option key={day.value} value={day.value}>{day.display}</option>
                ))}
            </select>
        </div>
    );
}

export default DayOfWeekSelection;