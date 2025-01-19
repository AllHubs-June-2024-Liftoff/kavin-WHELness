import mealPlanService from "@/services/mealPlanService.js";
import { useState, useEffect } from "react";
import Button from "./Button";

const daysOfTheWeekData = [
    { name: "Sunday", recipes: [] },
    { name: "Monday", recipes: [] },
    { name: "Tuesday", recipes: [] },
    { name: "Wednesday", recipes: [] },
    { name: "Thursday", recipes: [] },
    { name: "Friday", recipes: [] },
    { name: "Saturday", recipes: [] },
];

const MealPlanUI = () => {
    const [daysOfTheWeek, setDaysOfTheWeek] = useState(daysOfTheWeekData);

    useEffect(() => {
        fetch("http://localhost:8080/meal-plan/test-meal-plan")
        .then((response) => response.json())
        .then((data) => {
            if (data && data.mealPlanRecipes) {
                const updatedDays = daysOfTheWeekData.map((day) => (
                    {
                    ...day,
                    recipes: [],
                }));

                data.mealPlanRecipes.forEach((mpr) => {
                    const matchingDay = updatedDays.find(
                        (d) => d.name.toUpperCase() === mpr.dayOfTheWeek.toUpperCase()
                    );

                    if (matchingDay) {
                        matchingDay.recipes.push(mpr.recipe);
                    }
                });

                setDaysOfTheWeek(updatedDays);
            }
        })
        .catch((error) => {
            console.error("Error fetching meal plan:", error);
        });
    }, []);

    const handleAddRecipeClick = (dayName) => {
        console.log(`Add recipe to ${dayName}`);
    };

    return (
    <div>
        <h2 className="mealplan-header">Meal Plan</h2>
        <div className="meal-plan-ui-container border rounded">
            {daysOfTheWeek.map(day => (
                <div key={day.name} className="card m-4">
                    <h2 className="text-start">{day.name}</h2>
                    {day.recipes.map((recipe, index) => (
                        <div key={index} className="recipe-item">
                            <span>{recipe.name}</span>
                        </div>
                    ))}
                        <div className="text-end">
                            <Button 
                                label="Add Recipe" 
                                onClick={() => handleAddRecipeClick(day.name)}
                            />
                        </div>
                </div>
                
            ))}
            <hr />
        </div>
    </div>
    );
}

export default MealPlanUI
