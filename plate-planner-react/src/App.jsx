import Header from './components/Header.jsx';
import RecipeList from './components/RecipeList.jsx';
import Nav from './components/Nav.jsx';
import './App.css'

function App() {
  return (
    <div className='App'>
      <Header />
      <Nav />
      <RecipeList />
    </div>
  )
}

export default App
