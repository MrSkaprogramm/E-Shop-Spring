let position = 0;
const slidesToShow = 3;
const slidesToScroll = 2;
const container = document.querySelector('flowers-new');
const track = document.querySelector('flowers-new-track');
const btnPrev = document.querySelector('btn_prev');
const btnNext = document.querySelector('btn_next');
const itemsCount = document.querySelectorAll('flowers-new-variant').length;
const itemWidth = container.clientWidth / slidesToShow;
const movePosition = slidesToScroll * itemWidth;

item.foreach((item) => {
    item.style.minWidth = '${itemWidth}px'
});

btnNext.addEventListener('click', () => {
    const itemsLeft = itemsCount - (Math.abs(position) + slidesToShow * itemWidth) / itemWidth;

    position -= itemsLeft >= slidesToScroll ? movePosition : itemsLeft * itemWidth;

    setPosition();
    checkBtns();
});

btnPrev.addEventListener('click', () => {
    const itemsLeft = Math.abs(position) / itemWidth;

    position += itemsLeft >= slidesToScroll ? movePosition : itemsLeft * itemWidth;

    setPosition();
    checkBtns();
});

const setPosition = () => {
    track.style.transform = 'translateX(${position}px)';
};

const checkBtns = () => {
    btnPrev.disabled = position === 0;
    btnNext.disabled = position <= -(itemsCount - slidesToShow) * itemWidth;
};

checkBtns();

