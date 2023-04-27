import { render, screen, fireEvent } from '@testing-library/react';
import App from './App';
import TestRenderer from 'react-test-renderer';
import AddCar from './component/AddCar';
// 테스트 케이스는  AddCar 컴포넌트의 스냅샷을 생성하고 이전 스냅샷과 비교함
test('renders a snapshot', () => {
  const tree = TestRenderer.create(<AddCar/>).toJSON();
  expect(tree).toMatchSnapshot();
});


// 이테스트 파일은 App 컴포넌트를 렌더링하고 컴포넌트가 'learn react' 텍스트를 포함하는 링크 요소를 렌더링하는지 확인
// 이테스트는 vreate-recatapp에 기본적으로 제공되는 리액트 테스팅 라이브러리 를 사용함
// fireEvent.click 을 이용해 특정 DOM 요소에 대해 클릭 이벤트를 생성,DOM 요소는 모달 폼을 여는 버튼, getByText 로 쿼리를 찾을 수 있음
// 모달 대화상자가 열리고 모달 폼 헤더 텍스트인 'New Car' 텍스트가 렌더링 됐는지 확인함
// 모달 대화상자는 getByRole 쿼리로 찾음
// 이를 이용해 지정한 역할의 요소를 쿼리하고 모달 폼에 이용한 MUI 대화 상자 컴포넌트에 dialog 역할이 있는지 확인 할 수 있음.
test('open add car modal form', async () => {
  render(<App />);
  fireEvent.click(screen.getByText('New Car'));
  expect(screen.getByRole('dialog')).toHaveTextContent('New car');
});

