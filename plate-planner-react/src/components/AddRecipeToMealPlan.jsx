import { useState } from "react";
import {
    MDBBtn,
    MDBModal,
    MDBModalDialog,
    MDBModalContent,
    MDBModalHeader,
    MDBModalTitle,
    MDBModalBody,
    MDBModalFooter,
} from 'mdb-react-ui-kit';
import Button from "./Button";
import mealPlanService from "../services/mealPlanService";
import DayOfWeekSelection from "./DayOfWeekSelection";

function AddRecipeToMealPlan({ recipeId }) {
    const [basicModal, setBasicModal] = useState(false);
    const [error, setError] = useState(null);
    const toggleOpen = () => setBasicModal(!basicModal);
    const [selectedMealPlan, setSelectedMealPlan] = useState("");
    const [selectedDay, setSelectedDay] = useState("");
    const [mealPlanId, setMealPlanId] = useState(1);

    const handleDayChange = (day) => {
        setSelectedDay(day);
    }

    const addToMealPlan = async () => {
        if (!selectedDay) {
            setError("Please select a day of the week.");
            return;
        }
        setError(null);

        try {
            const response = await mealPlanService.addRecipeOnDay(mealPlanId, recipeId, selectedDay);

            if (response.status !== 200 && response.status !== 201 && response.status !== 204) {
                throw new Error("Could not add recipe to that day");
            }
            console.log("success: ", response.data);
            setBasicModal(false);
            setSelectedMealPlan("");
            alert("Recipe added successfully!");
        } catch (error) {
            console.error("Error adding recipe:", error);
            setError(error.message);
        }
    };

    return (
        <>
        <Button label="Add Recipe to Meal Plan" onClick={toggleOpen} />

        <MDBModal open={basicModal} onClose={() => setBasicModal(false)} tabIndex='-1'>
            <MDBModalDialog>
                <MDBModalContent>
                    <MDBModalHeader>
                        <MDBModalTitle>Add Recipe to the Meal Plan</MDBModalTitle>
                        <MDBBtn className="btn-close" color='none' onClick={toggleOpen}></MDBBtn>
                    </MDBModalHeader>
                    <MDBModalBody>
                        <DayOfWeekSelection selectedDay={selectedDay} onDayChange={handleDayChange} />
                        {error && <p style={{ color: 'red' }}>Error: {error}</p>}
                    </MDBModalBody>
                    <MDBModalFooter>
                        <Button label='Close' className="secondaryButton" onClick={toggleOpen} />
                        <Button label="Add to Meal Plan" onClick={addToMealPlan}></Button>
                    </MDBModalFooter>
                </MDBModalContent>
            </MDBModalDialog>
        </MDBModal>
        </>
    )
}

export default AddRecipeToMealPlan;