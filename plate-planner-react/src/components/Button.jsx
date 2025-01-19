const Button = ({ label = "Basic Button", onClick }) => {

    return (
        <div>
            <button type="button" className="btn btn-primary" onClick={onClick}>{label}</button>
        </div>
    )
}

export default Button;